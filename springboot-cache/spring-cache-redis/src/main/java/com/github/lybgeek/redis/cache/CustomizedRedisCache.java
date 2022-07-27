package com.github.lybgeek.redis.cache;

import cn.hutool.extra.spring.SpringUtil;

import com.github.lybgeek.redis.cache.config.CacheItemConfig;
import com.github.lybgeek.redis.cache.model.CacheContainer;
import com.github.lybgeek.redis.cache.service.CacheService;
import com.github.lybgeek.redis.cache.util.ThreadTaskHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @description: 自定义redis缓存
 **/
@Slf4j
public class CustomizedRedisCache extends RedisCache {


    private RedisOperations redisOperations;

    private static final Lock REFRESH_CACKE_LOCK = new ReentrantLock();

    private CacheService getCacheSupport(){
        return SpringUtil.getBean(CacheService.class);
    }


    public CustomizedRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, RedisOperations redisOperations) {
        super(name, cacheWriter,cacheConfig);
        this.redisOperations = redisOperations;
    }


    @Override
    public ValueWrapper get(final Object key) {
        ValueWrapper valueWrapper = super.get(key);
        if(!ObjectUtils.isEmpty(valueWrapper)){
            CacheItemConfig cacheItemConfig = CacheContainer.getCacheItemConfigByCacheName(this.getName());
            long preLoadTimeSecond = cacheItemConfig.getPreLoadTimeSecond();
            String cacheKey = this.createCacheKey(key);
            Long ttl = this.redisOperations.getExpire(cacheKey, TimeUnit.SECONDS);
            if(null != ttl && ttl <= preLoadTimeSecond){
                log.info("key:{} ttl:{} preloadSecondTime:{}",cacheKey,ttl,preLoadTimeSecond);
                if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
                    log.info("do not need to refresh");
                }
                else {
                    ThreadTaskHelper.run(() -> {
                        REFRESH_CACKE_LOCK.lock();
                        try {
                            if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
                                log.info("do not need to refresh");
                            }
                            else {
                                log.info("refresh key:{}", cacheKey);
                                CustomizedRedisCache.this.getCacheSupport().refreshCacheByKey(CustomizedRedisCache.super.getName(), key.toString());
                                ThreadTaskHelper.removeRefreshCacheTask(cacheKey);
                            }

                        }
                        finally {
                            REFRESH_CACKE_LOCK.unlock();
                        }
                    });
                }
            }
        }
        return valueWrapper;
    }
}
