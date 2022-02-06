package com.github.lybgeek.transcase.aopsort;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class AopAspect {


    @Around(value = " execution (* com.github.lybgeek.transcase.aopsort..*.*(..))")
    public Object around(ProceedingJoinPoint pjp){

        try {
            System.out.println("这是一个切面");
           return pjp.proceed();
        } catch (Throwable throwable) {
            log.error("{}",throwable);
        }

        return null;
    }
}
