package top.huajieyu001.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;

/**
 * @Author huajieyu
 * @Date 2026/3/21 16:02
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@Component
public class RateLimiter {

    @Resource
    private DefaultRedisScript<Integer> rateLimitLuaScript;

    @Resource
    private RedisTemplate redisTemplate;

    public boolean tryAcquire(String key, Long maxRequests, Long window) {
        try{
            Long result = (Long) redisTemplate.execute(rateLimitLuaScript, Collections.singletonList(key), maxRequests, window, System.currentTimeMillis());
            log.info("访问限流接口，调用Lua脚本结果为 {}", result);
            return result != null && result == 1L;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}