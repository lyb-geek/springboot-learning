package com.github.lybgeek.common.swagger.version.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    //主版本号，起始值为1
    int majorVersion() default 1;
    //次版本号，起始值为0
    int minorVersion() default 0;
    //修订版本号，起始值为0
    int revisionVersion() default 0;
    //是否使用版本号管理
    boolean useVersion() default true;
 
}