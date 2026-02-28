package top.huajieyu001.registry;

import org.springframework.stereotype.Component;

/**
 * @Author huajieyu
 * @Date 2026/2/28 23:48
 * @Version 1.0
 * @Description TODO
 */
@Component
public class LogRegistry {

    public Registry registry(){
        return new Registry();
    }

    class Registry{

    }
}
