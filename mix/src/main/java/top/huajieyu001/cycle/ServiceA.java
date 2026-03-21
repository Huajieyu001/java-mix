package top.huajieyu001.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @Author huajieyu
 * @Date 2026/3/22 0:17
 * @Version 1.0
 * @Description TODO
 */
@Service
public class ServiceA {

    private ServiceB serviceB;

    @Autowired
    @Lazy
    public ServiceA(ServiceB serviceB) {
        this.serviceB = serviceB;
    }

}
