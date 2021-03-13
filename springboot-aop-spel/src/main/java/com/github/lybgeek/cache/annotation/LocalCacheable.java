package com.github.lybgeek.cache.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LocalCacheable {
    String key() default "";
    String condition() default "";

}
