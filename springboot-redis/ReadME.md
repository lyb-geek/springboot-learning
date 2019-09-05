本例主要演示springboot和redis集成，主要实现的功能点有
- redis分布式锁 

第一种方案：通过org.springframework.integration.redis.util.RedisLockRegistry进行实现

第二种方案：通过lua脚本和redis实现，详情查看com.github.lybgeek.redis.util.RedisLockUtils

- redis缓存

第一种方案:通过与spring cache集成，@Cacheable、@CachePut 和 @CacheEvict，按不同业务配置不同的过期时间，
其配置可以查看com.github.lybgeek.redis.config.RedisConfig

第二种方案：通过自定义缓存注解 @RedisCache来实现
