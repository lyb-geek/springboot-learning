package com.github.lybgeek.redis.annotation;

import com.github.lybgeek.redis.enu.CacheOperateType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {

  CacheOperateType type();

  String cacheKeyPrefix() default "";

  long expireTime() default 1800;

  boolean allEntries() default false;

}
