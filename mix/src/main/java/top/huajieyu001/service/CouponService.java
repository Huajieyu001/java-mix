package top.huajieyu001.service;

/**
 * @Author huajieyu
 * @Date 2026/3/20 23:39
 * @Version 1.0
 * @Description TODO
 */
public interface CouponService {

    void initCoupon(Integer couponId, Integer stock);

    void getCoupon(Integer userId, Integer couponId);

    void getCouponWithLock(Integer userId, Integer couponId);
}