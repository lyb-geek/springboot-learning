package com.github.lybgeek.advisor;


import com.github.lybgeek.advisor.util.MethodParameterUtil;
import com.github.lybgeek.model.Customer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

public class CustomerMethodInterceptor implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        HandlerMethod handlerMethod = new HandlerMethod(invocation.getThis(),invocation.getMethod());
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        MethodParameterUtil.fillParamValueWithId(methodParameters,invocation.getArguments(),Customer.class);
        return invocation.proceed();
    }
}
