package com.github.lybgeek.pipeline.spring.annotation;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public @interface Pipeline {

    Class consumePipelinesService();

    String consumePipelinesMethod();

    Class[] args() default {};

    int order();
}
