package top.huajieyu001.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.huajieyu001.service.impl.BadTransactionServiceImpl;

import javax.annotation.Resource;

/**
 * @Author huajieyu
 * @Date 2026/3/22 1:08
 * @Version 1.0
 * @Description TODO
 */
@RestController
@RequestMapping("/trans")
public class TransactionalController {

    @Resource
    private BadTransactionServiceImpl badTransactionService;

    @GetMapping("bad")
    public void bad() {
        badTransactionService.bad();
    }

    @GetMapping("propagation")
    public void propagation() {
        badTransactionService.propagation();
    }

    @GetMapping("good")
    public void good() {
        badTransactionService.good();
    }

    @GetMapping("self")
    public void self() {
        badTransactionService.invokeSelf();
    }

    @GetMapping("thread")
    public void thread() {
        badTransactionService.multiThread();
    }
}
