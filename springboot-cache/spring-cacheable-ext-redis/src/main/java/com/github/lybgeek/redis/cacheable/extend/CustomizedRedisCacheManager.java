package com.github.lybgeek.redis.cacheable.extend;


import com.github.lybgeek.redis.cacheable.extend.model.CachedInvocation;
import com.github.lybgeek.redis.cacheable.extend.utils.EventBusHelper;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class CustomizedRedisCacheManager extends RedisCacheManager implements BeanFactoryAware {

    private Map<String, RedisCacheConfiguration> initialCacheConfigurations;

    private RedisTemplate cacheRedisTemplate;

    private RedisCacheWriter cacheWriter;

    private DefaultListableBeanFactory beanFactory;

    private RedisCacheConfiguration defaultCacheConfiguration;

    protected CachedInvocation cachedInvocation;


    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations,RedisTemplate cacheRedisTemplate) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.initialCacheConfigurations = initialCacheConfigurations;
        this.cacheRedisTemplate = cacheRedisTemplate;
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        //采用spring事件驱动亦可
        //EventBusHelper.register(this);
    }

    public Map<String, RedisCacheConfiguration> getInitialCacheConfigurations() {
        return initialCacheConfigurations;
    }

    @Override
    protected Collection<RedisCache> loadCaches() {
        List<RedisCache> caches = new LinkedList<>();

        for (Map.Entry<String, RedisCacheConfiguration> entry : getInitialCacheConfigurations().entrySet()) {
            caches.add(createRedisCache(entry.getKey(), entry.getValue()));
        }
        return caches;
    }

    @Override
    public RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
       CustomizedRedisCache customizedRedisCache = new CustomizedRedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfiguration);
       return customizedRedisCache;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    public RedisTemplate getCacheRedisTemplate() {
        return cacheRedisTemplate;
    }

    public CachedInvocation getCachedInvocation() {
        return cachedInvocation;
    }


    //@Subscribe
    @EventListener
    private void doWithCachedInvocationEvent(CachedInvocation cachedInvocation){
        this.cachedInvocation = cachedInvocation;
    }
}