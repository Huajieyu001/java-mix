package top.huajieyu001.exception;

/**
 * @Author huajieyu
 * @Date 2026/3/21 16:23
 * @Version 1.0
 * @Description TODO
 */
public class RateLimitException extends RuntimeException {
    public RateLimitException(String message) {
        super(message);
    }
}
