package com.github.lybgeek.circuitbreaker.spi.proxy;


import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.lybgeek.circuitbreaker.fallback.FallbackFactory;
import com.github.lybgeek.circuitbreaker.fallback.exception.CircuitBreakerException;
import com.github.lybgeek.circuitbreaker.fallback.model.DefaultFallBackResponse;
import com.github.lybgeek.circuitbreaker.spi.proxy.model.CircuitBreakerFallback;
import com.github.lybgeek.circuitbreaker.spi.proxy.model.CircuitBreakerInvocation;
import com.github.lybgeek.circuitbreaker.spi.proxy.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CircuitBreakerInvoker {

    public Object proceed(CircuitBreakerInvocation circuitBreakerInvocation) throws Throwable {

       Method method = circuitBreakerInvocation.getMethod();

        if ("equals".equals(method.getName())) {
            try {
                Object otherHandler = circuitBreakerInvocation.getArgs().length > 0 && circuitBreakerInvocation.getArgs()[0] != null
                        ? Proxy.getInvocationHandler(circuitBreakerInvocation.getArgs()[0]) : null;
                return equals(otherHandler);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else if ("hashCode".equals(method.getName())) {
            return hashCode();
        } else if ("toString".equals(method.getName())) {
            return toString();
        }


        Object result = null;

        String contextName = "spi_circuit_breaker@@";

        String className = ClassUtils.getClassName(circuitBreakerInvocation.getTarget());
        String resourceName = contextName + className + "." + method.getName();


        Entry entry = null;
        try {
            ContextUtil.enter(contextName);
            entry = SphU.entry(resourceName, EntryType.OUT, 1, circuitBreakerInvocation.getArgs());
            result = circuitBreakerInvocation.proceed();
        } catch (Throwable ex) {
            return doFallBack(ex, entry, circuitBreakerInvocation);
        } finally {
            if (entry != null) {
                entry.exit(1, circuitBreakerInvocation.getArgs());
            }
            ContextUtil.exit();
        }

        return result;
    }

    private static Object doFallBack(Throwable ex, Entry entry,CircuitBreakerInvocation invocation) throws Throwable {
        // fallback handle
        if (!BlockException.isBlockException(ex)) {
            Tracer.traceEntry(ex, entry);
        }
        CircuitBreakerFallback circuitBreakerFallback = invocation.getCircuitBreakerFallback();
        if (!void.class.isAssignableFrom(circuitBreakerFallback.getFallback())) {
            try {
                Object fallbackInstance = SpringUtil.getBean(circuitBreakerFallback.getFallback());
                Object fallbackResult = null;
                try {
                    fallbackResult = circuitBreakerFallback.getFallback().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes())
                            .invoke(fallbackInstance, invocation.getArgs());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return fallbackResult;
            } catch (IllegalAccessException e) {
                // shouldn't happen as method is public due to being an
                // interface
                throw new AssertionError(e);
            } catch (InvocationTargetException e) {
                throw new AssertionError(e.getCause());
            }
        } else if (!void.class.isAssignableFrom(circuitBreakerFallback.getFallbackFactory())) {
            FallbackFactory fallbackFactory = (FallbackFactory) SpringUtil.getBean(circuitBreakerFallback.getFallbackFactory());
            Object fallbackResult = null;
            try {
                Object rpcclient = fallbackFactory.create(ex);
                Method rpcCallbackMethod = rpcclient.getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes());
                rpcCallbackMethod.setAccessible(true);
                fallbackResult = rpcCallbackMethod.invoke(rpcclient, invocation.getArgs());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return fallbackResult;

        } else {
            // throw exception if fallbackFactory is null
            if(ex instanceof BlockException){
                throw new CircuitBreakerException(DefaultFallBackResponse.TOO_MANY_REQUESTS_CODE,DefaultFallBackResponse.DEFAULT_MSG);
            }
            throw ex;
        }
    }



}
