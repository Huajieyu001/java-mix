package top.huajieyu001.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author huajieyu
 * @Date 2026/3/21 14:52
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@Service
public class RetryServer {

    private static int EX_COUNTER = 0;

    public String send(String msg) {
        if (EX_COUNTER < 3) {
            log.info("EX_COUNTER:" + EX_COUNTER);
            EX_COUNTER++;
            throw new RuntimeException("Ex counter is " + EX_COUNTER);
        }

        log.info("Server sent success: {}", msg);
        return "success";
    }
}
