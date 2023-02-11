package com.github.lybgeek.aop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

@Aspect
public class EchoAspect {

    @Before(value = "execution(* com.github.lybgeek.aop.service.EchoService.echo(..))")
    public void before(JoinPoint joinPoint){
        System.out.println("USE AOP BY ASPECT WITH ARGS: " + Arrays.toString(joinPoint.getArgs()));

    }
}
