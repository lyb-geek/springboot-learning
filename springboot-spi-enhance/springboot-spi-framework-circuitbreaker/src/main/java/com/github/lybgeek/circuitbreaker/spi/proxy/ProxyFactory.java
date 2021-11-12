package com.github.lybgeek.circuitbreaker.spi.proxy;





public interface ProxyFactory {

    Object createProxy(Object target);

}
