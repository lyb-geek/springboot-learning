package com.github.lybgeek.el.test.aspect;


import com.github.lybgeek.el.plugin.ExpressionPlugin;
import com.github.lybgeek.el.test.annotation.Vip;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@RequiredArgsConstructor
public class VipAspect {

    private final ExpressionPlugin expressionPlugin;
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();


    @Around("@annotation(vip)")
    public Object doVip(ProceedingJoinPoint pjp, Vip vip) throws Throwable {
        Signature signature = pjp.getSignature();
        if (isVip(pjp, vip, signature)){
            return pjp.proceed();
        }

        throw new RuntimeException("您不是会员,无权使用该接口");


    }

    private boolean isVip(ProceedingJoinPoint pjp, Vip vip, Signature signature) throws Throwable {
        if(signature instanceof MethodSignature){
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
            if(parameterNames != null && parameterNames.length > 0){
                Object[] args = pjp.getArgs();
                Map<String,Object> rootObject = new HashMap<>();
                for(int i = 0; i < args.length; i++){
                    rootObject.put(parameterNames[i],args[i]);
                }
                if(StringUtils.hasText(vip.expression())){
                    return expressionPlugin.evaluateExpression(rootObject, vip.expression(),Boolean.class);

                }
            }

        }
        return false;
    }
}
