package com.github.lybgeek.sentinel.aspect;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.BaseWebMvcConfig;
import com.github.lybgeek.common.exception.BizException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 因项目使用项自定义全局异常，而sentinel统计异常数是在拦截器的afterCompletion执行，
 * 因为自定义全局异常会先于拦截器的afterCompletion之前执行，会导致afterCompletion无法收集到异常信息，
 * 因而使降级规则生效
 *
 * 因为切面会先于自定义全局异常之前执行，我们可以自定一个切面，手动触发sentinel的异常计数.
 *
 *  sentinel计数逻辑查看下如下
 *
 *@see com.alibaba.csp.sentinel.adapter.spring.webmvc.AbstractSentinelInterceptor#afterCompletion(HttpServletRequest, javax.servlet.http.HttpServletResponse, Object, Exception)
 *
 **/
@Aspect
@Component
public class StatisticsExceptionCountAspect {

    @Autowired
    @Lazy
    private BaseWebMvcConfig baseWebMvcConfig;

    @Pointcut("execution(* com.github.lybgeek.sentinel.controller..*.*(..))")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp)  {
        try {
            Object result = pjp.proceed();
            return result;
        } catch (Throwable throwable) {
            traceException(throwable);
            throwException(throwable);
        }

        return null;
    }

    /**
     * 抛出异常
     * @param throwable
     */
    private void throwException(Throwable throwable){
        if(throwable instanceof BizException){
            throw (BizException)throwable;
        }else if(throwable instanceof RuntimeException){
            throw (RuntimeException)throwable;
        } else if(throwable instanceof Error){
            throw (Error)throwable;
        }else{
            throw new RuntimeException(throwable);
        }

    }

    /**
     * 统计异常
     * @param ex
     */
    private void traceException(Throwable ex) {
        Entry entry = getEntryInRequest();
        if (entry != null) {
            Tracer.traceEntry(ex, entry);
        }
    }
    protected Entry getEntryInRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        Object entryObject = request.getAttribute(baseWebMvcConfig.getRequestAttributeName());
        return entryObject == null ? null : (Entry)entryObject;
    }
}
