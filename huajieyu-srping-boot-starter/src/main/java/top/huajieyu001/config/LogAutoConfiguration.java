package top.huajieyu001.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.huajieyu001.registry.LogRegistry;
import top.huajieyu001.service.LogService;
import top.huajieyu001.service.impl.LogServiceImpl;

/**
 * @Author huajieyu
 * @Date 2026/2/28 23:46
 * @Version 1.0
 * @Description TODO
 */
@Configuration
@ConditionalOnBean(LogRegistry.class)
public class LogAutoConfiguration {

    @Autowired
    LogProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public LogService logService(){
        if(!properties.isEnable()){
            throw new RuntimeException("请启用日志再使用");
        }
        return new LogServiceImpl(properties.getPrefix());
    }
}
