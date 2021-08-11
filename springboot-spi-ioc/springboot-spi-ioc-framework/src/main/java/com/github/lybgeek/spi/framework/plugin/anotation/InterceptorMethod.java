package com.github.lybgeek.spi.framework.plugin.anotation;


import com.github.lybgeek.spi.framework.plugin.interceptor.Interceptor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterceptorMethod {

 Class<? extends Interceptor>[] interceptorClasses() default {};
}
