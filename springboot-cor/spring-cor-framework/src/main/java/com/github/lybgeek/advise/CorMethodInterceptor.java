package com.github.lybgeek.advise;


import com.github.lybgeek.cor.CorHandlerInterceptor;
import com.github.lybgeek.cor.model.Invocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CorMethodInterceptor implements MethodInterceptor {

    private CorHandlerInterceptor corHandlerInterceptor;

    public CorMethodInterceptor(CorHandlerInterceptor corHandlerInterceptor) {
        this.corHandlerInterceptor = corHandlerInterceptor;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Invocation invoker = Invocation.builder()
                .args(invocation.getArguments())
                .method(invocation.getMethod())
                .target(invocation.getThis()).build();

        return corHandlerInterceptor.invoke(invoker);
    }
}
