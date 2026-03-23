package top.huajieyu001.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.huajieyu001.constants.RedisConstants;

import javax.annotation.Resource;

/**
 * @Author huajieyu
 * @Date 2026/3/20 23:39
 * @Version 1.0
 * @Description TODO
 */
@Component
@Slf4j
public class InitCouponUtils {

    @Resource
    private RedisTemplate redisTemplate;

    public void initCoupon(int couponId, int stock) {
        String hashKey = couponId + "";
        log.info("hashKey is {}, stock is {}", hashKey, stock);
        redisTemplate.opsForHash().put(RedisConstants.COUPON_STOCK_KEY, hashKey, stock);
    }
}
