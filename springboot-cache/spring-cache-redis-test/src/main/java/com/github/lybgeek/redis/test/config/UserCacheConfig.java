package com.github.lybgeek.redis.test.config;


import com.github.lybgeek.redis.cache.config.CacheItemConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserCacheConfig {

    @Bean
    public List<CacheItemConfig> userCacheItemConfigList(){
        List<CacheItemConfig> cacheItemConfigs = new ArrayList<>();

        CacheItemConfig cacheItemConfig = CacheItemConfig.builder().cacheName("user")
                .expiredTimeSecond(10).preLoadTimeSecond(5).build();

        cacheItemConfigs.add(cacheItemConfig);

        return cacheItemConfigs;

    }
}
