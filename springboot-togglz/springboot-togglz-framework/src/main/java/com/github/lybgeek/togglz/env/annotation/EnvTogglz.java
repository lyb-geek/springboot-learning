package com.github.lybgeek.togglz.env.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface EnvTogglz {

    String activeEnv() default "${lybgeek.togglz.env:dev}";
}
