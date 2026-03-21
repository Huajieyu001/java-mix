package top.huajieyu001.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import top.huajieyu001.enums.MyLockEnum;
import top.huajieyu001.enums.MyUnlockEnum;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author huajieyu
 * @Date 2026/3/21 18:03
 * @Version 1.0
 * @Description TODO
 */
public class MyLock implements Lock {

    private static final Logger log = LoggerFactory.getLogger(MyLock.class);

    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> lockLuaScript;

    private DefaultRedisScript<Long> unlockLuaScript;

    private DefaultRedisScript<Long> watchDogLuaScript;

    private String key;

    private UUID id;

    private long leaseTime = 30;

    private static final Map<String, MyEntry> EXPIRATION_RENEWAL_MAP = new ConcurrentHashMap<>();

    private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
            r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("my-lock-watchdog");
                return t;
            }
    );

    public MyLock(RedisTemplate redisTemplate,
                  DefaultRedisScript<Long> lockLuaScript,
                  DefaultRedisScript<Long> unlockLuaScript,
                  DefaultRedisScript<Long> watchDogLuaScript,
                  String key) {
        this.redisTemplate = redisTemplate;
        this.lockLuaScript = lockLuaScript;
        this.unlockLuaScript = unlockLuaScript;
        this.watchDogLuaScript = watchDogLuaScript;
        this.key = key;
        if (redisTemplate == null || lockLuaScript == null || unlockLuaScript == null || watchDogLuaScript == null || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("params null or empty");
        }
        this.id = UUID.randomUUID();
    }

    @Override
    public void lock() {
        while (true) {
            if (tryLock()) {
                return;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted while waiting for lock", e);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(-1, null);
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {
        return tryLock(waitTime, -1, unit);
    }

    public boolean tryLock(long waitTime, long releaseTime, TimeUnit unit) throws InterruptedException {
        if (tryAcquire(releaseTime, unit)) {
            return true;
        }

        if(waitTime < 0 || unit == null) {
            return false;
        }
        long startTime = System.currentTimeMillis();
        waitTime = unit.toMillis(waitTime);

        while (true) {
            if (tryAcquire(releaseTime, unit)) {
                return true;
            }

            if (System.currentTimeMillis() - startTime > waitTime) {
                return false;
            }

            Thread.sleep(50);
        }
    }

    public boolean tryAcquire(long releaseTime, TimeUnit unit) throws InterruptedException {
        try {
            long ttl;
            boolean watchDog = false;
            if (releaseTime == -1L || unit == null) {
                ttl = 30;
                watchDog = true;
            } else {
                ttl = unit.toSeconds(releaseTime);
            }
            long threadId = Thread.currentThread().getId();
            Long result = (Long) redisTemplate.execute(lockLuaScript, Collections.singletonList(key), id + ":" + threadId, ttl);

            if (result != null && result >= MyLockEnum.Success.getValue()) {
                log.info("重入锁获取成功，当前重入次数为{}, threadId:{}, id:{}", result, threadId, id);
                if (watchDog) {
                    startWatchDog();
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        try {
            Long result = (Long) redisTemplate.execute(unlockLuaScript, Collections.singletonList(key), id + ":" + Thread.currentThread().getId());
            if (result == null) {
                throw new IllegalStateException("unlock fail, result is null");
            } else if (result >= MyUnlockEnum.Success.getValue()) {
                log.info("unlock:重入锁计数器-1, threadId:{}, id:{}", Thread.currentThread().getId(), id);
                if (result == 0L) {
                    stopWatchDog();
                }
            } else {
                throw new IllegalStateException("unlock fail");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private void startWatchDog() {
        long threadId = Thread.currentThread().getId();
        ScheduledFuture<?> future = this.threadPool.scheduleAtFixedRate(() -> {
            renew(this.leaseTime, TimeUnit.SECONDS, threadId);
            log.info("续期中...........................");
        }, this.leaseTime / 3L, this.leaseTime / 3L, TimeUnit.SECONDS);
        MyEntry entry = new MyEntry();
        entry.setThreadId(threadId);
        entry.setFuture(future);
        log.info("已开启自动续期.............................");
        EXPIRATION_RENEWAL_MAP.putIfAbsent(key, entry);
    }

    private void stopWatchDog() {
        MyEntry entry = EXPIRATION_RENEWAL_MAP.get(key);
        if (entry != null) {
            entry.getFuture().cancel(true);
            EXPIRATION_RENEWAL_MAP.remove(key);
            log.info("已取消续期.............................");
        }
    }

    private void renew(long time, TimeUnit unit, long threadId) {
        unit.toSeconds(time);
        Long result = (Long) redisTemplate.execute(watchDogLuaScript, Collections.singletonList(key), id + ":" + threadId, time);
        if (result == null || result == -9) {
            stopWatchDog();
        }
    }

    public static class MyEntry {

        private long threadId;

        private ScheduledFuture<?> future;

        public long getThreadId() {
            return threadId;
        }

        public void setThreadId(long threadId) {
            this.threadId = threadId;
        }

        public ScheduledFuture<?> getFuture() {
            return future;
        }

        public void setFuture(ScheduledFuture<?> future) {
            this.future = future;
        }
    }
}