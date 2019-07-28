package com.github.lybgeek.proxy;

import com.github.lybgeek.annotaiton.BingLogService;
import com.github.lybgeek.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
@Slf4j
public class SpringServiceProxy {

    private Class<?> targetClz;

    public Object getInstance(Class<?> targetClz){
        this.targetClz = targetClz;
        ProxyFactory proxyFactory = new ProxyFactory();
        try {
            proxyFactory.setTarget(targetClz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(),e);
        }
        proxyFactory.addAdvice((MethodInterceptor) methodInvocation -> {
            boolean isAnnotation =  targetClz.isAnnotationPresent(BingLogService.class);
            Object result = methodInvocation.proceed();
            if(isAnnotation){
                BingLogService bingLogService =  targetClz.getAnnotation(BingLogService.class);
                boolean logEnabled = bingLogService.value();
                if(logEnabled){
                    String methodName = methodInvocation.getMethod().getName();
                    Object[] args = methodInvocation.getArguments();
                    LogUtil.INSTACNE.saveLog(methodName, args, result);
                }

            }
            return result;
        });

        return proxyFactory.getProxy();
    }
}
