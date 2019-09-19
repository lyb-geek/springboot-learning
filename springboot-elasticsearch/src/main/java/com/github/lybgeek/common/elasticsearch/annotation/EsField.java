package com.github.lybgeek.common.elasticsearch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface EsField {

  @AliasFor("name")
  String value() default "";

  String type() default "keyword";

  @AliasFor("value")
  String name() default "";

  boolean index() default true;

  boolean store() default false;

  String search_analyzer() default "";

  String analyzer() default "";


}