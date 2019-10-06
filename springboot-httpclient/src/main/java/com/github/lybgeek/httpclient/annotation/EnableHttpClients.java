package com.github.lybgeek.httpclient.annotation;

import com.github.lybgeek.httpclient.registrar.HttpClientRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value= HttpClientRegistrar.class)
public @interface EnableHttpClients {
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
}
