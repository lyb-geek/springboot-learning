package com.github.lybgeek.buffertrigger.handler;


import com.github.lybgeek.buffertrigger.model.Result;

public interface BatchConsumerTriggerHandler<T,R> {

    Result<R> handle(T request, String bizNo);

   default void closeBufferTrigger(){}
}
