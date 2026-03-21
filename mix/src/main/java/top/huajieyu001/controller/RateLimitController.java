package top.huajieyu001.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.huajieyu001.annotation.RateLimit;

/**
 * @Author huajieyu
 * @Date 2026/3/21 16:27
 * @Version 1.0
 * @Description TODO
 */
@RestController
@RequestMapping("/rate")
public class RateLimitController {

    @GetMapping("/index")
    @RateLimit(value = "RateLimitController.index", window = 10000, maxRequests = 2)
    public String index() {
        return "Success";
    }
}
