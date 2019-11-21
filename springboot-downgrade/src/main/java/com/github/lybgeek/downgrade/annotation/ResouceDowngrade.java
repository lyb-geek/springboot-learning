package com.github.lybgeek.downgrade.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResouceDowngrade {

  int maxThreshold() default 3;

  Class fallbackClass();

  String fallbackMethod() default "";

  String resouceId();

}
