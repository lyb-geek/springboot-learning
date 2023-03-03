package com.github.lybgeek.producer.annotation;


import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Value("${userCache.topic}")
@Documented
public @interface UserCacheTopic {
}
