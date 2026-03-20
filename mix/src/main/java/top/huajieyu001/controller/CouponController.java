package top.huajieyu001.controller;

import org.springframework.web.bind.annotation.*;
import top.huajieyu001.service.CouponService;

import javax.annotation.Resource;

/**
 * @Author huajieyu
 * @Date 2026/3/20 23:49
 * @Version 1.0
 * @Description TODO
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @PostMapping("/init")
    public void init(Integer couponId, Integer stock){
        if(couponId == null || stock == null){
            throw new RuntimeException("<UNK>");
        }
        couponService.initCoupon(couponId, stock);
    }

    @GetMapping("/getCoupon")
    public void getCoupon(Integer userId, Integer couponId){
        if(couponId == null || userId == null){
            throw new RuntimeException("<UNK>");
        }
        couponService.getCoupon(userId, couponId);
    }

    @GetMapping("/getCouponWithLock")
    public void getCouponWithLock(Integer userId, Integer couponId){
        if(couponId == null || userId == null){
            throw new RuntimeException("<UNK>");
        }
        couponService.getCouponWithLock(userId, couponId);
    }
}
