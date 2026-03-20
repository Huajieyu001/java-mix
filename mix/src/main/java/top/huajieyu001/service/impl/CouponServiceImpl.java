package top.huajieyu001.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import top.huajieyu001.RedisConstants;
import top.huajieyu001.service.CouponService;
import top.huajieyu001.util.InitCouponUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void initCoupon(Integer couponId, Integer stock) {
        initCouponUtils.initCoupon(couponId, stock);
    }

    @Override
    public void getCoupon(Integer userId, Integer couponId) {
        List<String> list = Arrays.asList(RedisConstants.COUPON_SYNC_QUEUE_KEY, RedisConstants.COUPON_STOCK_KEY, RedisConstants.COUPON_SUCCESS_LIST_PREFIX + couponId);
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

    @Override
    public void getCouponWithLock(Integer userId, Integer couponId) {
        RLock lock = redissonClient.getLock(RedisConstants.COUPON_LOCK_PREFIX + couponId);
        lock.lock();
        try {
            String userIdStr = userId.toString();
            String couponIdStr = couponId.toString();
            Map successList = redisTemplate.opsForHash().entries(RedisConstants.COUPON_SUCCESS_LIST_PREFIX + couponIdStr);
            if (successList == null) {
                return;
            }
            Object o = successList.get(userIdStr);
            if (o != null) {
                // 已经抢过券
                return;
            }
            // 没有抢过券，才执行后续操作
            Map stockList = redisTemplate.opsForHash().entries(RedisConstants.COUPON_STOCK_KEY);
            Object o1 = stockList.get(couponIdStr);
            if (o1 == null) {
                throw new RuntimeException("券不存在");
            }

            Integer stock = (Integer) o1;
            if(stock <= 0){
                log.error("库存已抢完");
                return;
            }

            // 扣减库存
            redisTemplate.opsForHash().increment(RedisConstants.COUPON_STOCK_KEY, couponIdStr, -1);

            // 插入抢券成功列表
            redisTemplate.opsForHash().put(RedisConstants.COUPON_SUCCESS_LIST_PREFIX + couponIdStr, userIdStr, 1);

            // 插入同步队列
            redisTemplate.opsForHash().putIfAbsent(RedisConstants.COUPON_SYNC_QUEUE_KEY, userIdStr, couponIdStr);
        } finally {
            lock.unlock();
        }
    }
}
