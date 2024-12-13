package com.github.lybgeek.pf4j.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface AutoPluginConfig {

      String id() default "";

      String description() default "";

      String className() default "";

      String version();

      String provider() default "";

      String dependencies() default "";

      String requires() default "";
}
