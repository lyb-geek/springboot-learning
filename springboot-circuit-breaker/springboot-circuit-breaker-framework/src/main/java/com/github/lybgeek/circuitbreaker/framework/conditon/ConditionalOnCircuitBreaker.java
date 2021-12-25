package com.github.lybgeek.circuitbreaker.framework.conditon;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

import static com.github.lybgeek.circuitbreaker.framework.properties.CircuitBreakerMappingProperties.PREFIX;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = PREFIX,name = "enabled",havingValue = "true",matchIfMissing = true)
public @interface ConditionalOnCircuitBreaker {
}
