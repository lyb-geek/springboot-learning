package com.github.lybgeek.httpclient.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.PARAMETER)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RemotePathParam {
    String value();
}
