package com.github.lybgeek.circuitbreaker.spi.proxy;


import java.lang.reflect.Proxy;

public class CircuitBreakerProxyFactory implements ProxyFactory{
    @Override
    public Object createProxy(Object target) {
        if(target.getClass().isInterface() || Proxy.isProxyClass(target.getClass())){
            return new CircuitBreakerJdkProxy().getProxy(target);
        }
        return new CircuitBreakerCglibProxy().getProxy(target);
    }
}
