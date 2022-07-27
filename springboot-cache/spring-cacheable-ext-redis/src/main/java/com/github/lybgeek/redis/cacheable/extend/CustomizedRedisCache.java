package com.github.lybgeek.redis.cacheable.extend;

import cn.hutool.core.util.ObjectUtil;
import com.github.lybgeek.redis.cacheable.extend.model.CachedInvocation;
import com.github.lybgeek.redis.cacheable.extend.utils.CacheHelper;
import com.github.lybgeek.redis.cacheable.extend.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @description: 自定义redis缓存
 **/
@Slf4j
public class CustomizedRedisCache extends RedisCache {

    private ReentrantLock lock = new ReentrantLock();

    public CustomizedRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter,cacheConfig);
    }

    @Override
    @Nullable
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper = super.get(key);
        CachedInvocation cachedInvocation = CacheHelper.getCacheManager().getCachedInvocation();
        long preLoadTimeSecond = cachedInvocation.getMetaData().getPreLoadTimeSecond();
        if(ObjectUtil.isNotEmpty(valueWrapper) && preLoadTimeSecond > 0){
            String cacheKey = createCacheKey(key);
            RedisTemplate cacheRedisTemplate = CacheHelper.getCacheManager().getCacheRedisTemplate();
            Long ttl = cacheRedisTemplate.getExpire(cacheKey, TimeUnit.SECONDS);
            if(ObjectUtil.isNotEmpty(ttl) && ttl <= preLoadTimeSecond){
                log.info(">>>>>>>>>>> cacheKey：{}, ttl: {},preLoadTimeSecond: {}",cacheKey,ttl,preLoadTimeSecond);
                ThreadPoolUtils.execute(()->{
                     lock.lock();
                     try{
                         CacheHelper.refreshCache(super.getName());
                     }catch (Exception e){
                         log.error("{}",e.getMessage(),e);
                     }finally {
                         lock.unlock();
                     }
                });
            }


        }
        return valueWrapper;
    }




}
