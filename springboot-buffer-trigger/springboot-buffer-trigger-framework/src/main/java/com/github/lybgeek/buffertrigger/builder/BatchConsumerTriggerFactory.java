package com.github.lybgeek.buffertrigger.builder;


import com.github.lybgeek.buffertrigger.handler.BatchConsumerTriggerHandler;
import com.github.lybgeek.buffertrigger.handler.DelegateBatchConsumerTriggerHandler;
import com.github.lybgeek.buffertrigger.model.DataExchange;
import com.github.phantomthief.collection.BufferTrigger;
import com.github.phantomthief.collection.impl.BatchConsumerTriggerBuilder;
import com.github.phantomthief.util.ThrowableConsumer;

import java.util.List;

public interface BatchConsumerTriggerFactory {

    default <T,R> BatchConsumerTriggerBuilder<DataExchange<T,R>> builder(){
        return null;
    }

    default <T,R> BufferTrigger<DataExchange<T,R>> getTrigger(ThrowableConsumer<List<DataExchange<T,R>>, Exception> consumer, String bufferTriggerBizType){
        if(!support(bufferTriggerBizType)){
           return null;
        }
       return builder().setConsumerEx(consumer).build();
    }

    boolean support(String bufferTriggerBizType);

    default <T,R> BatchConsumerTriggerHandler<T,R> getTriggerHandler(ThrowableConsumer<List<DataExchange<T,R>>, Exception> consumer, String bufferTriggerBizType){
        BufferTrigger<DataExchange<T, R>> trigger = getTrigger(consumer, bufferTriggerBizType);
        return new DelegateBatchConsumerTriggerHandler<>(trigger);
    }


}
