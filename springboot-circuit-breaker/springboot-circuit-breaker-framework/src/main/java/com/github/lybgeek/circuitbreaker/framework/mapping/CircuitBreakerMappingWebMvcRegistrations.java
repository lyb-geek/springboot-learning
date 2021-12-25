package com.github.lybgeek.circuitbreaker.framework.mapping;


import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class CircuitBreakerMappingWebMvcRegistrations implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CircuitBreakerMappingHandlerMapping();
    }
}
