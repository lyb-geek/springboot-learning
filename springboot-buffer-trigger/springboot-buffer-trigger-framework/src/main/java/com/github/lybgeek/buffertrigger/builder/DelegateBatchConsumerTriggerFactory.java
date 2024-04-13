package com.github.lybgeek.buffertrigger.builder;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.buffertrigger.model.DataExchange;
import com.github.lybgeek.buffertrigger.property.BufferTriggerProperties;
import com.github.phantomthief.collection.BufferTrigger;
import com.github.phantomthief.util.ThrowableConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class DelegateBatchConsumerTriggerFactory implements BatchConsumerTriggerFactory, InitializingBean {

    private final BufferTriggerProperties bufferTriggerProperties;

    private List<BatchConsumerTriggerFactory> batchConsumerTriggerFactories;


    @Override
    public <T,R> BufferTrigger<DataExchange<T,R>> getTrigger(ThrowableConsumer<List<DataExchange<T,R>>, Exception> consumer, String bufferTriggerBizType){
        if(CollectionUtil.isEmpty(batchConsumerTriggerFactories)){
            return null;
        }

        for (BatchConsumerTriggerFactory batchConsumerTriggerFactory : batchConsumerTriggerFactories) {
            BufferTrigger<DataExchange<T,R>> bufferTrigger = batchConsumerTriggerFactory.getTrigger(consumer, bufferTriggerBizType);
            if(bufferTrigger != null){
                return bufferTrigger;
            }
        }

        return null;

    }




    @Override
    public boolean support(String bufferTriggerBizType) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(bufferTriggerProperties == null || CollectionUtil.isEmpty(bufferTriggerProperties.getConsumeQueueTriggerProperties())){
            return;
        }
        batchConsumerTriggerFactories = new ArrayList<>();
        bufferTriggerProperties.getConsumeQueueTriggerProperties().forEach(batchConsumeBlockingQueueTriggerProperty -> {
            BatchConsumerTriggerFactory batchConsumerTriggerFactory = new BatchConsumerTriggerTemplate(batchConsumeBlockingQueueTriggerProperty);
            batchConsumerTriggerFactories.add(batchConsumerTriggerFactory);
        });

    }
}
