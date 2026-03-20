package top.huajieyu001.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huajieyu
 * @Date 2026/3/21 2:31
 * @Version 1.0
 * @Description TODO
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();

        config.useSingleServer()
                .setAddress("redis://192.168.101.128:6379")
                .setDatabase(0)
                .setPassword("123456");

        return Redisson.create(config);
    }
}
