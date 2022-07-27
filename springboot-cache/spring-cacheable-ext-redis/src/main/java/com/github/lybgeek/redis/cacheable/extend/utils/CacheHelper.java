package com.github.lybgeek.redis.cacheable.extend.utils;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.redis.cacheable.annotation.LybGeekCacheable;
import com.github.lybgeek.redis.cacheable.constant.CacheConstant;
import com.github.lybgeek.redis.cacheable.extend.CustomizedRedisCacheManager;
import com.github.lybgeek.redis.cacheable.extend.init.CacheExpireTimeInit;
import com.github.lybgeek.redis.cacheable.extend.model.CacheMetaData;
import com.github.lybgeek.redis.cacheable.extend.model.CachedInvocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.Map;

/**
 * 缓存过期时间以及过期时缓存自动刷新核心类
 */
@Slf4j
public final class CacheHelper {


    public static CustomizedRedisCacheManager getCacheManager(){
        return SpringUtil.getBean(CacheConstant.CUSTOM_CACHE_MANAGER,CustomizedRedisCacheManager.class);
    }


    /**
     * {@link CacheExpireTimeInit}
     * @param lybGeekCacheable
     */
    public static void initExpireTime(LybGeekCacheable lybGeekCacheable){

        String[] cacheNames = getCacheNames(lybGeekCacheable);

        if(ArrayUtil.isNotEmpty(cacheNames)){
            Map<String, RedisCacheConfiguration> initialCacheConfigurations = getCacheManager().getInitialCacheConfigurations();
            for (String cacheName : cacheNames) {
                RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(lybGeekCacheable.expiredTimeSecond()));
                initialCacheConfigurations.put(cacheName,redisCacheConfiguration);
            }

        }


    }

    public static void initializeCaches(){
        getCacheManager().initializeCaches();
        log.info(">>>>>>>>>>>> 【{}】 set expireTime finished !", JSON.toJSONString(getCacheManager().getInitialCacheConfigurations()));
    }

    public static String[] getCacheNames(LybGeekCacheable lybGeekCacheable) {
        String[] cacheNames = lybGeekCacheable.cacheNames();
        if(ArrayUtil.isEmpty(cacheNames)){
            cacheNames = lybGeekCacheable.value();
        }
        return cacheNames;
    }



    public static void refreshCache(String cacheName){
        boolean isMatchCacheName  = isMatchCacheName(cacheName);
        if(isMatchCacheName){
            CachedInvocation cachedInvocation = getCacheManager().getCachedInvocation();
            boolean invocationSuccess;
            Object computed = null;
            try {
                computed = cachedInvocation.invoke();
                invocationSuccess = true;
            } catch (Exception ex) {
                invocationSuccess = false;
                log.error(">>>>>>>>>>>>>>>>> refresh cache fail",ex.getMessage(),ex);
            }

            if (invocationSuccess) {
                    Cache cache = getCacheManager().getCache(cacheName);
                    if(ObjectUtil.isNotEmpty(cache)){
                        Object cacheKey = cachedInvocation.getMetaData().getKey();
                        cache.put(cacheKey, computed);
                        log.info(">>>>>>>>>>>>>>>>>>>> refresh cache with cacheName-->【{}】，key--> 【{}】 finished !",cacheName,cacheKey);
                    }
            }
        }

    }


    private static boolean isMatchCacheName(String cacheName){
        CachedInvocation cachedInvocation = getCacheManager().getCachedInvocation();
        if(ObjectUtil.isEmpty(cachedInvocation)){
            log.warn("cachedInvocation is empty");
            return false;
        }
        CacheMetaData metaData = cachedInvocation.getMetaData();
        for (String name : metaData.getCacheNames()) {
            if(name.equals(cacheName)){
                return true;
            }
        }
        return true;
    }
}
