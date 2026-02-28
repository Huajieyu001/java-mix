package top.huajieyu001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.huajieyu001.service.LogService;

import javax.annotation.PostConstruct;

/**
 * @Author huajieyu
 * @Date 2026/3/1 0:51
 * @Version 1.0
 * @Description TODO
 */
@Service
public class UserService {

    @Autowired
    private LogService logService;

    @PostConstruct
    public void init(){
        logService.info("HAHAHAHAHHAHA");
    }
}
