package com.github.lybgeek.httpclient.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteHeader {
    String name();
    String value();
}
