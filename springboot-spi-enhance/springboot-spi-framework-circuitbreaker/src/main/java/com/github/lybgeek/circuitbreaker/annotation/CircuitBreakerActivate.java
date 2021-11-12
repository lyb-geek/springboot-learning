package com.github.lybgeek.circuitbreaker.annotation;


import com.github.lybgeek.spi.anotatation.Activate;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Activate
public @interface CircuitBreakerActivate {

    String spiKey();

    Class<?> fallback() default void.class;

    Class<?> fallbackFactory() default void.class;
}
