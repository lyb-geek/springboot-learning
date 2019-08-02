package com.github.lybgeek.annotaiton;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BingLogService {
    boolean value() default true;
}
