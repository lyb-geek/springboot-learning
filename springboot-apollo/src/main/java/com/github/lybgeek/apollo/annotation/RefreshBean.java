package com.github.lybgeek.apollo.annotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface RefreshBean {

    Class[] refreshFieldBeans() default {};
}
