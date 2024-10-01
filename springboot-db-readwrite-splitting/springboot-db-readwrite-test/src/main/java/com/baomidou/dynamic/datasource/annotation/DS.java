package com.baomidou.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * The core Annotation to switch datasource. It can be annotate at class or method.
 *
 * @author TaoYu Kanyuxia
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

    /**
     * groupName or specific database name or spring SPEL name.
     *
     * @return the database you want to switch
     */
    String value();
}