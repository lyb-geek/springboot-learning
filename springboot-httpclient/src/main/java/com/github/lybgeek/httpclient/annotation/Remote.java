package com.github.lybgeek.httpclient.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Remote {
    String url();
    RemoteHeader[] headers() default {@RemoteHeader(name = "", value = "")};

}
