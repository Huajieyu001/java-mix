package top.huajieyu001.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huajieyu
 * @Date 2026/3/22 1:12
 * @Version 1.0
 * @Description TODO
 */
@MapperScan("top.huajieyu001.mapper")
@Configuration
public class MybatisConfig {
}
