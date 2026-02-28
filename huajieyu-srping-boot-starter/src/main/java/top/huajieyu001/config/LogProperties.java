package top.huajieyu001.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huajieyu
 * @Date 2026/2/28 23:29
 * @Version 1.0
 * @Description TODO
 */
@Configuration
@ConfigurationProperties(prefix = "log")
public class LogProperties {

    private boolean enable = false;

    private String prefix;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}