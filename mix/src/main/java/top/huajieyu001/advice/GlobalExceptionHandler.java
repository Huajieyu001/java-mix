package top.huajieyu001.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.huajieyu001.exception.RateLimitException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author huajieyu
 * @Date 2026/3/21 16:22
 * @Version 1.0
 * @Description TODO
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Object handleRateLimitException(RateLimitException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        result.put("code", 429);
        return result;
    }
}
