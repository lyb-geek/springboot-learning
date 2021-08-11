package com.github.lybgeek.spi;


import com.github.lybgeek.spi.framework.anotation.Spi;

@Spi
public interface HelloService {

    String sayHello(String username);
}
