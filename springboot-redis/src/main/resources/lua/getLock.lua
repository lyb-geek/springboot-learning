local requestKey=KEYS[1]
local lockedKeys=KEYS[2]
local requestValue=ARGV[1]
local expireTime=ARGV[2]
local nowTime=ARGV[3]
if redis.call('get',requestKey)
then
    return 0
end
local lockedHash = redis.call('hkeys',lockedKeys)
for i=1, #lockedHash do
    if string.find(requestKey,lockedHash[i]) or string.find(lockedHash[i],requestKey)
    then
        local lockTime = redis.call('hget',lockedKeys,lockedHash[i])
        if (nowTime-lockTime) >= expireTime * 1000
        then
            redis.call('hdel',lockedKeys,lockedHash[i])
        else
            return 0
        end
    end
end
redis.call('set',requestKey,requestValue)
redis.call('expire',requestKey,expireTime)
redis.call('hset',lockedKeys,requestKey,nowTime)
return 1