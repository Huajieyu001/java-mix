package top.huajieyu001.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @Author huajieyu
 * @Date 2026/3/21 0:22
 * @Version 1.0
 * @Description TODO
 */
@Configuration
public class RedisLuaConfig {

    @Bean
    public DefaultRedisScript<Integer> couponLuaScript() {
        DefaultRedisScript<Integer> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/coupon.lua")));
        script.setResultType(Integer.class);
        return script;
    }

    @Bean
    public DefaultRedisScript<Integer> rateLimitLuaScript() {
        DefaultRedisScript<Integer> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/rateLimit.lua")));
        script.setResultType(Integer.class);
        return script;
    }
}
