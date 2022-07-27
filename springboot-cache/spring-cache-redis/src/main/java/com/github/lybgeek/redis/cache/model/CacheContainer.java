package com.github.lybgeek.redis.cache.model;

import com.github.lybgeek.redis.cache.config.CacheItemConfig;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @description: 缓存容器
 **/
public class CacheContainer {

    private static final String DEFAULT_CACHE_NAME = "default";

    private static final Map<String, CacheItemConfig> CACHE_CONFIG_HOLDER = new ConcurrentHashMap(){
        {
            put(DEFAULT_CACHE_NAME,new CacheItemConfig(){
                @Override
                public String getCacheName() {
                    return DEFAULT_CACHE_NAME;
                }

                @Override
                public long getExpiredTimeSecond() {
                    return 30;
                }

                @Override
                public long getPreLoadTimeSecond() {
                    return 25;
                }
            });
        }
    };

    public static void init(List<CacheItemConfig> cacheItemConfigs){
        if(CollectionUtils.isEmpty(cacheItemConfigs)){
            return;
        }
        cacheItemConfigs.forEach(cacheItemConfig -> {
            CACHE_CONFIG_HOLDER.put(cacheItemConfig.getCacheName(),cacheItemConfig);
        });

    }

    public static CacheItemConfig getCacheItemConfigByCacheName(String cacheName){
        if(CACHE_CONFIG_HOLDER.containsKey(cacheName)) {
            return CACHE_CONFIG_HOLDER.get(cacheName);
        }
        return CACHE_CONFIG_HOLDER.get(DEFAULT_CACHE_NAME);
    }

    public static List<CacheItemConfig> getCacheItemConfigs(){
        return CACHE_CONFIG_HOLDER
                .values()
                .stream()
                .filter(cacheItemConfig -> !cacheItemConfig.getCacheName().equals(DEFAULT_CACHE_NAME))
                .collect(Collectors.toList());
    }
}
