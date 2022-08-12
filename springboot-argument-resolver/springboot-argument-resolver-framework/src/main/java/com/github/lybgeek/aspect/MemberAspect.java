package com.github.lybgeek.aspect;


import com.github.lybgeek.advisor.util.MethodParameterUtil;
import com.github.lybgeek.model.Member;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;


@Aspect
public class MemberAspect {

    /**
     *
     * @param pjp
     * @return
     *
     * @within 和 @target:带有相应标注的所有类的任意方法，比如@Transactional
     * @annotation:带有相应标注的任意方法，比如@Transactional
     * @within和@target针对类的注解，@annotation针对方法的注解
     *
     * @args:参数带有相应标注的任意方法，比如@Transactiona
     */
    @SneakyThrows
    @Around(value = "@within(org.springframework.web.bind.annotation.RestController)")
    public Object around(ProceedingJoinPoint pjp){

        MethodSignature methodSignature =  (MethodSignature)pjp.getSignature();

        HandlerMethod handlerMethod = new HandlerMethod(pjp.getTarget(),methodSignature.getMethod());
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        MethodParameterUtil.fillParamValueWithId(methodParameters,pjp.getArgs(), Member.class);
        Object result = pjp.proceed();

        return result;

    }
}
