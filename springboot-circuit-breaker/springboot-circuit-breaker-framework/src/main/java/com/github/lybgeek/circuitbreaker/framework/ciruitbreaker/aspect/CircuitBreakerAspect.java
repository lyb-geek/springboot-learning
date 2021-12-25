package com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.aspect;


import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.lybgeek.circuitbreaker.framework.annotation.CircuitBreakerMapping;
import com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.core.AbstractCircuitBreakerAspectSupport;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

@Aspect
public class CircuitBreakerAspect extends AbstractCircuitBreakerAspectSupport {



    @Around("@annotation(circuitBreakerMapping)")
    public Object invokeResourceWithSentinel(ProceedingJoinPoint pjp, CircuitBreakerMapping circuitBreakerMapping) throws Throwable {
        Method originMethod = resolveMethod(pjp);
        CircuitBreakerMapping controllerCircuitBreakerMapping = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(),CircuitBreakerMapping.class);
        String baseResouceName = "lybgeek:";
        if(circuitBreakerMapping != null){
            baseResouceName = baseResouceName + controllerCircuitBreakerMapping.value()[0];
        }

        baseResouceName = baseResouceName + circuitBreakerMapping.value()[0];

        String resourceName = getResourceName(baseResouceName, originMethod);
        EntryType entryType = circuitBreakerMapping.entryType();
        int resourceType = circuitBreakerMapping.resourceType();
        Entry entry = null;
        try {
            String contextName = "lybgeek_circuitbreaker_context";
            RequestOriginParser parser = SpringUtil.getBean(RequestOriginParser.class);
            ContextUtil.enter(contextName,parser.parseOrigin(getRequest()));
            entry = SphU.entry(resourceName, resourceType, entryType, pjp.getArgs());
            Object result = pjp.proceed();
            return result;
        } catch (BlockException ex) {
            return handleBlockException(pjp, circuitBreakerMapping, ex);
        } catch (Throwable ex) {
            Class<? extends Throwable>[] exceptionsToIgnore = circuitBreakerMapping.exceptionsToIgnore();
            // The ignore list will be checked first.
            if (exceptionsToIgnore.length > 0 && exceptionBelongsTo(ex, exceptionsToIgnore)) {
                throw ex;
            }
            if (exceptionBelongsTo(ex, circuitBreakerMapping.exceptionsToTrace())) {
                traceException(ex, circuitBreakerMapping);
                return handleFallback(pjp, circuitBreakerMapping, ex);
            }

            // No fallback function can handle the exception, so throw it out.
            throw ex;
        } finally {
            if (entry != null) {
                entry.exit(1, pjp.getArgs());
            }
            ContextUtil.exit();
        }
    }
}
