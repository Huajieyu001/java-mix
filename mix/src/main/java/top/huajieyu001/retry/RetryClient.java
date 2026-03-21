package top.huajieyu001.retry;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author huajieyu
 * @Date 2026/3/21 14:52
 * @Version 1.0
 * @Description TODO
 */
@Service
public class RetryClient {

    @Resource
    private RetryServer retryServer;

    @Retryable(value = Throwable.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 1.5, maxDelay = 10000))
    public String send(String msg) {
        return retryServer.send(msg);
    }

    @Recover
    public String recover(Throwable e, String msg) {
        return "failed";
    }
}
