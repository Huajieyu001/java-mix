package top.huajieyu001.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.huajieyu001.retry.RetryClient;

import javax.annotation.Resource;

/**
 * @Author huajieyu
 * @Date 2026/3/21 15:03
 * @Version 1.0
 * @Description TODO
 */
@RestController
@RequestMapping("/retry")
public class RetryController {

    @Resource
    private RetryClient retryClient;

    @GetMapping("/send")
    public String send(String msg) {
        return retryClient.send(msg);
    }
}
