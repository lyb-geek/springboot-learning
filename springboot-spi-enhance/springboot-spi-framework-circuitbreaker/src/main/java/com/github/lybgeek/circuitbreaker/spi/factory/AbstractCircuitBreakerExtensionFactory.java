package com.github.lybgeek.circuitbreaker.spi.factory;


import com.github.lybgeek.circuitbreaker.spi.proxy.CircuitBreakerProxy;
import com.github.lybgeek.circuitbreaker.spi.proxy.ProxyFactory;
import com.github.lybgeek.spi.factory.ExtensionFactory;

public abstract class AbstractCircuitBreakerExtensionFactory implements ExtensionFactory {


    private ProxyFactory proxyFactory;

    public AbstractCircuitBreakerExtensionFactory(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    protected  <T> T getCircuitBreakerExtension(final String key, final Class<T> clazz){

        T target = getExtension(key,clazz);

        return (T) proxyFactory.createProxy(target);

    }
}
