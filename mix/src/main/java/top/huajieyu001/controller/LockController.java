package top.huajieyu001.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.huajieyu001.lock.MyLock;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author huajieyu
 * @Date 2026/3/21 19:15
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@RestController
@RequestMapping("/lock")
public class LockController {

//    @Resource
//    private MyLock myLock;

    @Resource
    RedissonClient redisson;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DefaultRedisScript<Long> lockLuaScript;
    @Resource
    private DefaultRedisScript<Long> unlockLuaScript;
    @Resource
    private DefaultRedisScript<Long> watchDogLuaScript;


    @GetMapping("/get")
    public String get(String key) {
        MyLock lock = new MyLock(redisTemplate, lockLuaScript, unlockLuaScript, watchDogLuaScript, key);
        lock.lock();
        return "Success";
    }

    @GetMapping("/multiLock")
    public String multiLock(String key) {
        RLock lock = redisson.getLock(key);
//        lock.tryLock(1, 2, TimeUnit.SECONDS)
        try{
            lock.lock();
            Thread.sleep(10000);
            lock.lock();
            Thread.sleep(10000);
            lock.lock();
            Thread.sleep(10000);
            lock.unlock();
            Thread.sleep(10000);
            lock.unlock();
            Thread.sleep(10000);
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Success";
    }

    @GetMapping("/release")
    public String release(String key) {
//        log.info("release key:{}", key);
//        myLock.unlock(key);
        return "Success";
    }

    @GetMapping("/getAndRelease")
    public String getAndRelease(String key) {
        MyLock lock = new MyLock(redisTemplate, lockLuaScript, unlockLuaScript, watchDogLuaScript, key);
        lock.lock();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return "Success";
    }
}