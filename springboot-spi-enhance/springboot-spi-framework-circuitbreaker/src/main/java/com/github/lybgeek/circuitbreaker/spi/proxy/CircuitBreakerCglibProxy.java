package com.github.lybgeek.circuitbreaker.spi.proxy;


import com.github.lybgeek.circuitbreaker.spi.proxy.model.CircuitBreakerInvocation;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CircuitBreakerCglibProxy implements CircuitBreakerProxy, MethodInterceptor {

    private Object target;

    @Override
    public Object getProxy(Object target) {
        this.target = target;
        return getProxy(target,Thread.currentThread().getContextClassLoader());
    }

    @Override
    public Object getProxy(Object target, ClassLoader classLoader) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);//回调函数  拦截器
        enhancer.setSuperclass(target.getClass());//设置代理对象的父类
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        CircuitBreakerInvocation invocation = new CircuitBreakerInvocation(target,method,objects);
        return new CircuitBreakerInvoker().proceed(invocation);
    }
}
