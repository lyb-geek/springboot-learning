package com.github.lybgeek.proxy;

import com.github.lybgeek.annotaiton.BingLogService;
import com.github.lybgeek.util.LogUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ServiceProxy implements MethodInterceptor {

    private Class<?> targetClz;

    public  Object getInstance(Class<?> targetClz){
        this.targetClz = targetClz;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClz);
        enhancer.setCallback(this);
        return  enhancer.create();
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        boolean isAnnotation =  this.targetClz.isAnnotationPresent(BingLogService.class);
        Object result = methodProxy.invokeSuper(obj,args);
        if(isAnnotation){
            BingLogService bingLogService =  this.targetClz.getAnnotation(BingLogService.class);
            boolean logEnabled = bingLogService.value();
            if(logEnabled){
                String methodName = method.getName();
                LogUtil.INSTACNE.saveLog(methodName, args, result);
            }

        }
        return result;
    }
}
