package com.github.lybgeek.circuitbreaker.spi.proxy;


import org.springframework.lang.Nullable;

public interface CircuitBreakerProxy {

    Object getProxy(Object target);

    Object getProxy(Object target,@Nullable ClassLoader classLoader);
}
