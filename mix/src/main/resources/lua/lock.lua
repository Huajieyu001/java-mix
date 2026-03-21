local key = KEYS[1]
local uniqueId = ARGV[1]
local ttl = tonumber(ARGV[2])

local lockNum = redis.call("HLEN", key)
local state = 1
-- 判断是否有人持有锁
if lockNum and lockNum > 0 then
    local value = redis.call("HGET", key, uniqueId)
    -- 判断持有锁的人是否是自己
    if not value then
        return -1
    end
end

-- 如果没人持有锁，或者持有锁的人是自己，则会走到这里
local result = redis.call("HINCRBY", key, uniqueId, 1)
if result > 0 then
    -- 设置过期时间
    redis.call("EXPIRE", key, ttl)
    return result
end
