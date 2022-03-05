package com.github.lybgeek.cor.handler.support;


import com.github.lybgeek.cor.handler.AbstarctHandler;
import com.github.lybgeek.cor.model.Invocation;

public class DefaultHandler implements AbstarctHandler {

    @Override
    public int getOrder() {
        return 200;
    }

    @Override
    public boolean preHandler(Invocation invocation) {
        System.out.println(String.format("spring->preHandler:【{%s}】",invocation));
        return true;
    }

    @Override
    public void afterCompletion(Invocation invocation) {
        System.out.println(String.format("spring->afterCompletion:【{%s}】",invocation));
    }


}
