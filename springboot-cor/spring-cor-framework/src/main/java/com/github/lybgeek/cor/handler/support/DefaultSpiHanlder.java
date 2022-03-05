package com.github.lybgeek.cor.handler.support;


import com.github.lybgeek.cor.handler.AbstarctHandler;
import com.github.lybgeek.cor.model.Invocation;
import lombok.extern.slf4j.Slf4j;

public class DefaultSpiHanlder implements AbstarctHandler {


    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public boolean preHandler(Invocation invocation) {
        System.out.println(String.format("spi->preHandler:【{%s}】",invocation));
        return true;
    }

    @Override
    public void afterCompletion(Invocation invocation) {
        System.out.println(String.format("spi->afterCompletion:【{%s}】",invocation));
    }


}
