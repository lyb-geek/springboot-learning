package com.github.lybgeek.redis.cache.service;

/**
 * @description: 刷新缓存接口
 **/
public interface CacheService {

	/**
	 * 刷新容器中所有值
	 * @param cacheName
     */
	void refreshCache(String cacheName);

	/**
	 * 按容器以及指定键更新缓存
	 * @param cacheName
	 * @param cacheKey
     */
	void refreshCacheByKey(String cacheName, String cacheKey);

}