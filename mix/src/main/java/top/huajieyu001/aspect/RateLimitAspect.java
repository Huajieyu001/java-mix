package top.huajieyu001.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.huajieyu001.annotation.RateLimit;
import top.huajieyu001.exception.RateLimitException;
import top.huajieyu001.handler.RateLimiter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author huajieyu
 * @Date 2026/3/21 16:13
 * @Version 1.0
 * @Description TODO
 */
@Aspect
@Component
@Slf4j
public class RateLimitAspect {

    @Resource
    private RateLimiter rateLimiter;

//    @Resource
//    private HttpServletRequest request;

    private static final String LIMIT_KEY = "LIMIT:";

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = rateLimit.value();
        if (key == null || "".equals(key)) {
            throw new RateLimitException("限流key不能为空");
        }

        key = LIMIT_KEY + key;

        boolean acquire = rateLimiter.tryAcquire(key, rateLimit.maxRequests(), rateLimit.window());
        if (!acquire) {
            log.warn("请求被限流...key = {}, window = {}, maxRequests = {}", key, rateLimit.window(), rateLimit.maxRequests());
            throw new RateLimitException("请求太频繁，稍后再试！");
        }

        return joinPoint.proceed();
    }
}
