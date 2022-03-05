package com.github.lybgeek.cor.test.interceptor;


import com.github.lybgeek.cor.handler.AbstarctHandler;
import com.github.lybgeek.cor.model.Invocation;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class HelloServiceSpiInterceptor implements AbstarctHandler {

    @Override
    public boolean preHandler(Invocation invocation) {
        Object[] args = invocation.getArgs();
        System.out.println("参数转换-->preHandler");
        for (int i = 0; i < args.length; i++) {
            if("lisi".equals(args[i])){
                args[i] = "李四";
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(Invocation invocation) {
        System.out.println("参数转换-->afterCompletion：" + Arrays.toString(invocation.getArgs()));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
