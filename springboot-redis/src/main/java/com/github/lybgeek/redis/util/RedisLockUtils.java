package com.github.lybgeek.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;

/**
 * redis工具类
 */
public class RedisLockUtils {

    static final Long SUCCESS = 1L;
    static final String LOCKED_HASH = "cs:lockedKeyHash";
    static final String GET_LOCK_LUA_RESOURCE = "/lua/getLock.lua";
    static final String RELEASE_LOCK_LUA_RESOURCE = "/lua/releaseLock.lua";
    static final Logger LOG = LoggerFactory.getLogger(RedisLockUtils.class);
    /**
     * 获取锁
     * @param redisTemplate
     * @param lockKey
     * @param requestValue
     * @param expireTime 单位：秒
     * @return
     */
    public static boolean getLock(RedisTemplate redisTemplate, String lockKey, String requestValue, Integer expireTime) {

        LOG.info("start run lua script，{{}} start request lock",lockKey);
        long start = System.currentTimeMillis();
        DefaultRedisScript<String> luaScript =new DefaultRedisScript<>();
        luaScript.setLocation(new ClassPathResource(GET_LOCK_LUA_RESOURCE));
        luaScript.setResultType(String.class);

        Object result = redisTemplate.execute(
                luaScript,
                Arrays.asList(lockKey, LOCKED_HASH),
                requestValue,
                String.valueOf(expireTime),
                String.valueOf(System.currentTimeMillis())
        );
        boolean getLockStatus = SUCCESS.equals(result);

        LOG.info("{{}} cost time {} ms，request lock result：{}",lockKey,(System.currentTimeMillis()-start), getLockStatus);
        return getLockStatus;
    }

    /**
     * 释放锁
     * @param redisTemplate
     * @param lockKey
     * @param requestValue
     * @return
     */
    public static boolean releaseLock(RedisTemplate redisTemplate, String lockKey, String requestValue) {

        DefaultRedisScript<String> luaScript =new DefaultRedisScript<>();
        luaScript.setLocation(new ClassPathResource(RELEASE_LOCK_LUA_RESOURCE));
        luaScript.setResultType(String.class);

        Object result = redisTemplate.execute(
                luaScript,
                Arrays.asList(lockKey, LOCKED_HASH),
                requestValue
        );
        boolean releaseLockStatus = SUCCESS.equals(result);

        LOG.info("{{}}release lock result：{}", lockKey, releaseLockStatus);
        return releaseLockStatus;
    }

}