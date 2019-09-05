local requestKey=KEYS[1]
local lockedKeys=KEYS[2]
local requestValue=ARGV[1]
if redis.call('get', requestKey) == requestValue
then
    redis.call('hdel', lockedKeys,requestKey)
    return redis.call('del',requestKey)
else
    return 0
end