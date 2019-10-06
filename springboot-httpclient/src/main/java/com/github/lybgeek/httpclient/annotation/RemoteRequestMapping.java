package com.github.lybgeek.httpclient.annotation;

import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteRequestMapping {
    String path();
    HttpclientTypeEnum type() default HttpclientTypeEnum.HTTP_CLIENT;
}
