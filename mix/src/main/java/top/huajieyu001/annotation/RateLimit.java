package top.huajieyu001.annotation;

import top.huajieyu001.enums.LimitType;

import java.lang.annotation.*;

/**
 * @Author huajieyu
 * @Date 2026/3/21 15:53
 * @Version 1.0
 * @Description TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimit {

    String value() default "";

    long maxRequests() default 100;

    long window() default 30000;

    LimitType type() default LimitType.API;
}
