## 本文主要演示如何扩展spring @Cacheable实现redis缓存失效以及缓存主动刷新

实现核心要点

> 1、扩展RedisCache以及RedisCacheManager

> 2、spring事件驱动

实现核心逻辑类：com.github.lybgeek.redis.cacheable.extend.utils.CacheHelper

参考：https://www.cnblogs.com/ASPNET2008/p/8733087.html