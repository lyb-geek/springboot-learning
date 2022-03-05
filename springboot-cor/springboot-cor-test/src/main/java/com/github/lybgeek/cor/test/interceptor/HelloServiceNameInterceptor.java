package com.github.lybgeek.cor.test.interceptor;


import com.github.lybgeek.cor.handler.AbstarctHandler;
import com.github.lybgeek.cor.model.Invocation;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class HelloServiceNameInterceptor implements AbstarctHandler {

    @Override
    public boolean preHandler(Invocation invocation) {
        Object[] args = invocation.getArgs();
        System.out.println("名称校验-->preHandler");
        for (Object arg : args) {
            if("张三".equals(arg)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(Invocation invocation) {
        System.out.println("名称校验-->afterCompletion：" + Arrays.toString(invocation.getArgs()));
    }

    @Override
    public int getOrder() {
        return 102;
    }
}
