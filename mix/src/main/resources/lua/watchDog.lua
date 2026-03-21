local key = KEYS[1]
local uniqueId = ARGV[1]
local ttl = ARGV[2]

local value = redis.call("HGET", key, uniqueId)
-- 如果获取不到，说明该锁不存在，或者锁不是自己的，要么key不匹配，要么uniqueId不匹配，不进行续期
if not value then
    return -9
end

-- 如果锁存在且是自己的，执行-1操作，进行续期
redis.call("EXPIRE", key, ttl)
return 1