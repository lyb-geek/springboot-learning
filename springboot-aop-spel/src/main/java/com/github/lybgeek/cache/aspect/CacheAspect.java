package com.github.lybgeek.cache.aspect;


import com.github.lybgeek.cache.LocalCache;
import com.github.lybgeek.cache.annotation.LocalCacheable;
import com.github.lybgeek.util.SpELParserUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Component
@Aspect
public class CacheAspect {


    @Around("@annotation(localCacheable)")
    public Object around(ProceedingJoinPoint pjp, LocalCacheable localCacheable) throws Throwable{
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = pjp.getArgs();
        Object result = pjp.proceed();
        String key = pjp.getTarget().getClass().getName() + "_" + method.getName() + "_" + args.length;

        if(!StringUtils.isEmpty(localCacheable.key())){
           key = SpELParserUtils.parse(method,args,localCacheable.key(),String.class);
        }

        System.out.println("key:"+key);

        if(!StringUtils.isEmpty(localCacheable.condition())){
            boolean condition = SpELParserUtils.parse(method,args,localCacheable.condition(),Boolean.class);
            if(condition){
                LocalCache.INSTANCE.put(key,result);
            }
        }else{
            LocalCache.INSTANCE.put(key,result);
        }

        return result;

    }
}
