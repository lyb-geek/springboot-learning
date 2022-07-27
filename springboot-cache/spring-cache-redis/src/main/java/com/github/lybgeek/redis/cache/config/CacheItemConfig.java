package com.github.lybgeek.redis.cache.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 缓存配置项
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CacheItemConfig implements Serializable {

    /**
     * 缓存容器名称
     */
    private String cacheName;
    /**
     * 缓存失效时间
     */
    private long expiredTimeSecond;
    /**
     * 当缓存存活时间达到此值时，主动刷新缓存
     */
    private long preLoadTimeSecond;

}
