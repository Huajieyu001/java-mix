package top.huajieyu001.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import top.huajieyu001.RedisConstants;
import top.huajieyu001.service.CouponService;
import top.huajieyu001.util.InitCouponUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author huajieyu
 * @Date 2026/3/20 23:47
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private InitCouponUtils initCouponUtils;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DefaultRedisScript<Integer> couponRedisScript;

    @Override
    public void initCoupon(Integer couponId, Integer stock) {
        initCouponUtils.initCoupon(couponId, stock);
    }

    @Override
    public void getCoupon(Integer userId, Integer couponId) {
        List<String> list = Arrays.asList(RedisConstants.COUPON_SYNC_QUEUE_KEY, RedisConstants.COUPON_STOCK_PREFIX, RedisConstants.COUPON_SUCCESS_LIST_PREFIX + couponId);
        Object result = redisTemplate.execute(couponRedisScript, list, couponId, userId);

        if (result == null) {
            throw new RuntimeException("<UNK>");
        }
        Integer code = (Integer) result;
        log.info("list = {}, userId = {}, couponId = {}, code = {}", list, userId, couponId, code);
        if (code <= 0) {
            String message = "优惠券抢券失败";
            if(code == -1) {
                message = "用户已经抢过优惠券";
            } else if (code == -2) {
                message = "优惠券不存在";
            }
            log.error(message);
        }
    }
}
