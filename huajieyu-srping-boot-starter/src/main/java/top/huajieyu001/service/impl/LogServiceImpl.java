package top.huajieyu001.service.impl;

import org.springframework.stereotype.Service;
import top.huajieyu001.service.LogService;

/**
 * @Author huajieyu
 * @Date 2026/2/28 23:36
 * @Version 1.0
 * @Description TODO
 */
public class LogServiceImpl implements LogService {

    private String prefix;

    public LogServiceImpl(){
        prefix = "";
    }

    public LogServiceImpl(String prefix){
        this.prefix = prefix;
    }

    @Override
    public void debug(Object obj) {
        System.out.println("[DEBUG] -- " + prefix + obj);
    }

    @Override
    public void info(Object obj) {
        System.out.println("[INFO] -- " + prefix + obj);
    }

    @Override
    public void warn(Object obj) {
        System.out.println("[WARN] -- " + prefix + obj);
    }

    @Override
    public void error(Object obj) {
        System.out.println("[ERROR] -- " + prefix + obj);
    }
}
