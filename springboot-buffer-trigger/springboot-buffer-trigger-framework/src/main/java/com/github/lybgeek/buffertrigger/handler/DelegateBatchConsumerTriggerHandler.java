package com.github.lybgeek.buffertrigger.handler;


import com.github.lybgeek.buffertrigger.model.DataExchange;
import com.github.lybgeek.buffertrigger.model.Result;
import com.github.phantomthief.collection.BufferTrigger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class DelegateBatchConsumerTriggerHandler<T, R> implements BatchConsumerTriggerHandler<T, R>{


    private final BufferTrigger<DataExchange<T, R>> bufferTrigger;



    @SneakyThrows
    @Override
    public Result<R> handle(T request, String bizNo) {
        DataExchange dataExchange = new DataExchange<>();
        dataExchange.setBizNo(bizNo);
        dataExchange.setRequest(request);
        CompletableFuture<Result> response = new CompletableFuture<>();
        dataExchange.setResponse(response);
        bufferTrigger.enqueue(dataExchange);
        return response.get();
    }


    @Override
    public void closeBufferTrigger() {
        // 触发该事件，关闭BufferTrigger，并将未消费的数据消费
       if(bufferTrigger != null){
           bufferTrigger.close();
       }
    }
}
