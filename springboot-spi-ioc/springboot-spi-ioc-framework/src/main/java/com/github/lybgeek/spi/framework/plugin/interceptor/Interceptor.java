package com.github.lybgeek.spi.framework.plugin.interceptor;


import com.github.lybgeek.spi.framework.plugin.model.Invocation;

public interface Interceptor {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    default boolean preHandle(Invocation invocation){
        return true;
    }

    default void afterCompletion(Invocation invocation){

    }

    int getOrder();


}
