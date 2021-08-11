package com.github.lybgeek.spi.framework.plugin.proxy;


import com.github.lybgeek.spi.framework.plugin.interceptor.Interceptor;
import com.github.lybgeek.spi.framework.plugin.model.InterceptorChain;
import com.github.lybgeek.spi.framework.plugin.model.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class SpiProxy implements InvocationHandler {

    private final Map<Class<?>, InterceptorChain> signatureMap;

    private final Object target;

    public SpiProxy(Object target,Map<Class<?>, InterceptorChain> signatureMap){
        this.signatureMap = signatureMap;
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果Object方法直接反射调用
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this, args);
        }
        boolean canPass = preHandle(method,args);
        Object result = null;
        if(canPass){
             result = method.invoke(target,args);
             afterCompletion(method,args);
        }

        return result;

    }

    private boolean preHandle(Method method, Object[] args){
        InterceptorChain interceptorChain = signatureMap.get(target.getClass());
        Invocation invocation = getInvocation(method,args);
        boolean canPass = true;
        if(!method.getName().equals(interceptorChain.getMethod().getName())){
            return false;
        }

        for (Interceptor interceptor : interceptorChain.getInterceptors()) {
            if(canPass){
                canPass = interceptor.preHandle(invocation);
            }
        }
        return canPass;
    }

    private void afterCompletion(Method method, Object[] args){
        InterceptorChain interceptorChain = signatureMap.get(target.getClass());
        Invocation invocation = getInvocation(method,args);
        if(method.getName().equals(interceptorChain.getMethod().getName())){
            for (Interceptor interceptor : interceptorChain.getInterceptors()) {
               interceptor.afterCompletion(invocation);
            }
        }


    }

    private Invocation getInvocation(Method method, Object[] args){
        Invocation invocation = new Invocation(target,method,args);
        return invocation;
    }
}
