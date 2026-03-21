local key = KEYS[1]
local uniqueId = ARGV[1]

local value = redis.call("HGET", key, uniqueId)
-- 如果获取不到，说明该锁不存在，或者锁不是自己的，要么key不匹配，要么uniqueId不匹配
if not value then
    return -9
end

-- 如果锁存在且是自己的，执行-1操作
local result = redis.call("HINCRBY", key, uniqueId, -1)
-- 判断锁的重入性，是-1还是彻底释放锁
if result < 1 then
    -- result小于1，彻底释放锁
    redis.call("DEL", key)
end

return result