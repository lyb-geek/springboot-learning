package com.github.lybgeek.plugin.spring.aop.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class MethodCostTimeAspect {


    @Pointcut("@annotation(com.github.lybgeek.plugin.spring.aop.annotation.MethodCostTime) && @within(org.pf4j.Extension)")
    public void pointcut(){

    }


    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint){
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            long end = System.currentTimeMillis();
            System.out.println("method:"+((MethodSignature)joinPoint.getSignature()).getMethod().getName()+" cost time:"+(end-start)+"ms");
        }

        return result;
    }
}
