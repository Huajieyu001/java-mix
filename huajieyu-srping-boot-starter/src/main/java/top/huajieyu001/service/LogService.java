package top.huajieyu001.service;

/**
 * @Author huajieyu
 * @Date 2026/2/28 23:35
 * @Version 1.0
 * @Description TODO
 */
public interface LogService {

    void debug(Object obj);

    void info(Object obj);

    void warn(Object obj);

    void error(Object obj);
}
