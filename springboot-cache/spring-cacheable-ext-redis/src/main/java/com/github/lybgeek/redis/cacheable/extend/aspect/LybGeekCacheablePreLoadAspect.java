package com.github.lybgeek.redis.cacheable.extend.aspect;


import cn.hutool.extra.spring.SpringUtil;
import com.github.lybgeek.redis.cacheable.annotation.LybGeekCacheable;
import com.github.lybgeek.redis.cacheable.constant.CacheConstant;
import com.github.lybgeek.redis.cacheable.extend.model.CacheMetaData;
import com.github.lybgeek.redis.cacheable.extend.model.CachedInvocation;
import com.github.lybgeek.redis.cacheable.extend.utils.EventBusHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

import static com.github.lybgeek.redis.cacheable.extend.utils.CacheHelper.getCacheNames;

/**
 * 拆成2个切面是为让LybGeekCacheableExpireAspect比LybGeekCacheablePreLoadAspect先执行
 */
@Component
@Aspect
@Slf4j
@Order(2)
public class LybGeekCacheablePreLoadAspect {

    @Autowired
    private ApplicationContext applicationContext;


    @SneakyThrows
    @Around(value = "@annotation(lybGeekCacheable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,LybGeekCacheable lybGeekCacheable){
        buildCachedInvocationAndPushlish(proceedingJoinPoint,lybGeekCacheable);
        Object result = proceedingJoinPoint.proceed();
        return result;

    }

    private void buildCachedInvocationAndPushlish(ProceedingJoinPoint proceedingJoinPoint,LybGeekCacheable lybGeekCacheable){
        Method method = this.getSpecificmethod(proceedingJoinPoint);
        String[] cacheNames = getCacheNames(lybGeekCacheable);
        Object targetBean = proceedingJoinPoint.getTarget();
        Object[] arguments = proceedingJoinPoint.getArgs();
        KeyGenerator keyGenerator = SpringUtil.getBean(CacheConstant.CUSTOM_CACHE_KEY_GENERATOR,KeyGenerator.class);
        Object key = keyGenerator.generate(targetBean, method, arguments);
        CachedInvocation cachedInvocation = CachedInvocation.builder()
                .arguments(arguments)
                .targetBean(targetBean)
                .targetMethod(method)
                .metaData(CacheMetaData.builder()
                        .cacheNames(cacheNames)
                        .key(key)
                        .expiredTimeSecond(lybGeekCacheable.expiredTimeSecond())
                        .preLoadTimeSecond(lybGeekCacheable.preLoadTimeSecond())
                        .build()
                )
                .build();
      //  EventBusHelper.post(cachedInvocation);
        applicationContext.publishEvent(cachedInvocation);
    }

    private Method getSpecificmethod(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // The method may be on an interface, but we need attributes from the
        // target class. If the target class is null, the method will be
        // unchanged.
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
        if (targetClass == null && pjp.getTarget() != null) {
            targetClass = pjp.getTarget().getClass();
        }
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the
        // original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }





}
