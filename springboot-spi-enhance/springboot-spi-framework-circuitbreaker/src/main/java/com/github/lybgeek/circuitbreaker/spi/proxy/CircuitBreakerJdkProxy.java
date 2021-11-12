package com.github.lybgeek.circuitbreaker.spi.proxy;


import com.github.lybgeek.circuitbreaker.spi.proxy.model.CircuitBreakerInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CircuitBreakerJdkProxy implements CircuitBreakerProxy, InvocationHandler {

    private Object target;

    @Override
    public Object getProxy(Object target) {
        this.target = target;
        return getProxy(target,Thread.currentThread().getContextClassLoader());
    }

    @Override
    public Object getProxy(Object target, ClassLoader classLoader) {
        this.target = target;
        return Proxy.newProxyInstance(classLoader,target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CircuitBreakerInvocation invocation = new CircuitBreakerInvocation(target,method,args);
        try {
            return new CircuitBreakerInvoker().proceed(invocation);
            //用InvocationTargetException包裹是java.lang.reflect.UndeclaredThrowableException问题
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }

    }
}
