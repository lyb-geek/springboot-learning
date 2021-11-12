package com.github.lybgeek.circuitbreaker.spi.proxy.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CircuitBreakerFallback {

    private Class fallback;

    private Class fallbackFactory;
}
