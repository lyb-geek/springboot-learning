package com.github.lybgeek.annotaiton;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindLog {

    boolean value() default true;
}
