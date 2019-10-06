package com.github.lybgeek.httpclient.annotation;

import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface HttpClient {
    HttpclientTypeEnum type();
}
