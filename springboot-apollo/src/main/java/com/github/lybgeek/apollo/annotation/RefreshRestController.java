package com.github.lybgeek.apollo.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshRestController {

    Class[] refreshFieldBeans() default {};
}
