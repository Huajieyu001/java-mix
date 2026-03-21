--
-- local key = KEYS[1]
-- local count = ARGV[1]
-- local time = ARGV[2]
--
-- local nowCount = redis.call("GET", key)
--
-- if nowCount and tonumber(nowCount) >= tonumber(count) then
--     return -1
-- end
--
-- local incr = redis.call("INCR", key)
-- if not incr then
--     return -2
-- end
--
-- local ex = redis.call("EXPIRE", key, time)
-- if ex then
--     return 1
-- end



local key = KEYS[1]
local maxRequests = tonumber(ARGV[1])
local window = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

-- 移除过期请求
redis.call("ZREMRANGEBYSCORE", key, 0, now - window)

-- 通过zset判断请求数
local currentNum = redis.call("ZCARD", key)

if currentNum >= maxRequests then
    return -1
end

-- 添加新请求信息到zset
local addResult = redis.call("ZADD", key, now, now .. "_" .. math.random())
if addResult ~= 1 then
    return -2
end
-- 设置过期时间，防止内存浪费
redis.call("PEXPIRE", key, window)
return 1
