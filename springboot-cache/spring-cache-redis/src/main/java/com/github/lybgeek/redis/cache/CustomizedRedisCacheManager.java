package com.github.lybgeek.redis.cache;


import com.github.lybgeek.redis.cache.config.CacheItemConfig;
import com.github.lybgeek.redis.cache.model.CacheContainer;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @description: 自定义的redis缓存管理器：支持方法上配置过期时间；支持热加载缓存：缓存即将过期时主动刷新缓存
 **/
public class CustomizedRedisCacheManager extends RedisCacheManager {

    private RedisCacheWriter redisCacheWriter;
    private RedisCacheConfiguration defaultRedisCacheConfiguration;
    private RedisOperations redisOperations;

    public CustomizedRedisCacheManager(
            RedisConnectionFactory connectionFactory,
            RedisOperations redisOperations,
            List<CacheItemConfig> cacheItemConfigList) {

        this(
                RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory),
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30)),
                cacheItemConfigList
                        .stream()
                        .collect(Collectors.toMap(CacheItemConfig::getCacheName, cacheItemConfig -> {
                            RedisCacheConfiguration cacheConfiguration =
                                    RedisCacheConfiguration
                                            .defaultCacheConfig()
                                            .entryTtl(Duration.ofSeconds(cacheItemConfig.getExpiredTimeSecond()))
                                            .prefixKeysWith(cacheItemConfig.getCacheName());
                            return cacheConfiguration;
                        }))
        );
        this.redisOperations = redisOperations;
        CacheContainer.init(cacheItemConfigList);

    }
    public CustomizedRedisCacheManager(
            RedisCacheWriter redisCacheWriter
            ,RedisCacheConfiguration redisCacheConfiguration,
            Map<String, RedisCacheConfiguration> redisCacheConfigurationMap) {
        super(redisCacheWriter,redisCacheConfiguration,redisCacheConfigurationMap);
        this.redisCacheWriter = redisCacheWriter;
        this.defaultRedisCacheConfiguration = redisCacheConfiguration;
    }


    @Override
    public Cache getCache(String name) {

        Cache cache = super.getCache(name);
        if(null == cache){
            return cache;
        }
        CustomizedRedisCache redisCache = new CustomizedRedisCache(
                name,
                this.redisCacheWriter,
                this.defaultRedisCacheConfiguration,
                this.redisOperations
        );
        return redisCache;

    }
}
