package top.huajieyu001.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.huajieyu001.postgres.service.BadTransService;

import javax.annotation.Resource;

/**
 * @Author huajieyu
 * @Date 2026/3/22 18:47
 * @Version 1.0
 * @Description TODO
 */
@RestController
@RequestMapping("/ds")
public class DatasourceController {

    @Resource
    private BadTransService badTransService;

    @GetMapping("master")
    public String master() {
        return badTransService.listMaster().toString();
    }

    @GetMapping("slave")
    public String slave() {
        return badTransService.listSlave().toString();
    }

    @GetMapping("m1")
    public String m1() {
        badTransService.addMaster();
        return "m1 success";
    }

    @GetMapping("s1")
    public String s1() {
        badTransService.addSlave();
        return "s1 success";
    }

    @GetMapping("m2")
    public String m2() {
        return badTransService.m2();
    }

    @GetMapping("s2")
    public String s2() {
        return badTransService.s2();
    }

    @GetMapping
    public String addAll() {
        return badTransService.addAll();
    }
}
