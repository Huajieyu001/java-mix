-- 抢券同步队列
local syncQueueKey = KEYS[1]
-- 资源库存,
local stockKey = KEYS[2]
-- 抢券成功列表
local successListKey = KEYS[3]
-- 优惠券的id
local couponId = ARGV[1]
-- 用户的id
local userId = ARGV[2]

-- if couponId then
--     return couponId
-- end

-- 检查该用户的优惠券列表中是否抢过券
local couponNum = redis.call("HGET", successListKey, userId)
if couponNum ~= false and tonumber(couponNum) >= 1 then
    return "-1"
end

-- 检查是否存在优惠券
local stockNum = redis.call("HGET", stockKey, couponId)
if stockNum == false then
    return "-2"
end

-- 检查是否仍有库存，也就是数量是否大于0
if tonumber(stockNum) < 1 then
    return "-3"
end

-- 开始抢券，写入抢券成功列表
local listNum = redis.call("HSET", successListKey, userId, 1)
if listNum == false or tonumber(listNum) < 1 then
    return "-4"
end

-- 扣减库存
local subStock = redis.call("HINCRBY", stockKey, couponId, -1)
if tonumber(subStock) < 0 then
    -- 扣减后库存小于0，说明抢券失败
    return "-5"
end

-- 写入抢券成功列表和扣减库存成功，说明都正常，则可以把用户id和券id写入同步队列
local result = redis.call("HSETNX", syncQueueKey, userId, couponId)
if result > 0 then
    return ARGV[1] ..""
end

return "-6"



