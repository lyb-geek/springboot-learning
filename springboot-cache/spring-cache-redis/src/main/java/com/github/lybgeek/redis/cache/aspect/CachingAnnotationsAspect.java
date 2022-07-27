package com.github.lybgeek.redis.cache.aspect;

import cn.hutool.core.util.ArrayUtil;
import com.github.lybgeek.redis.cache.InvocationRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;


/**
 * @description: 缓存拦截，用于注册方法信息
 */
@Aspect
@Component
public class CachingAnnotationsAspect {


	@Autowired
	private InvocationRegistry cacheRefreshSupport;

	private <T extends Annotation> List<T> getMethodAnnotations(AnnotatedElement ae, Class<T> annotationType) {
		List<T> anns = new ArrayList<T>(2);
		// look for raw annotation
		T ann = ae.getAnnotation(annotationType);
		if (ann != null) {
			anns.add(ann);
		}
		// look for meta-annotations
		for (Annotation metaAnn : ae.getAnnotations()) {
			ann = metaAnn.annotationType().getAnnotation(annotationType);
			if (ann != null) {
				anns.add(ann);
			}
		}
		return (anns.isEmpty() ? null : anns);
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

	@Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
	public void pointcut(){}

	@Around("pointcut()")
	public Object registerInvocation(ProceedingJoinPoint joinPoint) throws Throwable {

		Method method = this.getSpecificmethod(joinPoint);

		List<Cacheable> annotations = this.getMethodAnnotations(method,Cacheable.class);

		Set<String> cacheSet = new HashSet<String>();
		for (Cacheable cacheables : annotations) {
			if(ArrayUtil.isNotEmpty(cacheables.value())){
				cacheSet.addAll(Arrays.asList(cacheables.value()));
			}else if(ArrayUtil.isNotEmpty(cacheables.cacheNames())){
				cacheSet.addAll(Arrays.asList(cacheables.cacheNames()));
			}
		}
		cacheRefreshSupport.registerInvocation(joinPoint.getTarget(), method, joinPoint.getArgs(), cacheSet);
		return joinPoint.proceed();

	}


}