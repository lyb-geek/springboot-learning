package com.github.lybgeek.registercenter.discovery.annotation;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Value("${spring.application.name: }")
public @interface ApplicationName {
}
