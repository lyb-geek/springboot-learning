package com.github.lybgeek.aspect;


import com.github.lybgeek.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {


    @Around(value = "@annotation(log)")
    public Object around(ProceedingJoinPoint pjp, Log log) throws Throwable {

        System.out.println("mockLog:-->" + Arrays.asList(pjp.getArgs()));

         Object result = pjp.proceed();

         return result;

    }
}
