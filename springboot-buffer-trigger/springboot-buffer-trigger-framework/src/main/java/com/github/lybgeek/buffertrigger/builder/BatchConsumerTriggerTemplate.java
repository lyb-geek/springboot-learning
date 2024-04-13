package com.github.lybgeek.buffertrigger.builder;


import cn.hutool.core.util.StrUtil;
import com.github.lybgeek.buffertrigger.model.DataExchange;
import com.github.lybgeek.buffertrigger.property.BatchConsumeBlockingQueueTriggerProperty;
import com.github.phantomthief.collection.impl.BatchConsumerTriggerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.time.Duration;

@RequiredArgsConstructor
public class BatchConsumerTriggerTemplate implements BatchConsumerTriggerFactory {

    protected final BatchConsumeBlockingQueueTriggerProperty property;


    @Override
    public <T,R> BatchConsumerTriggerBuilder<DataExchange<T,R>> builder() {
        Assert.notNull(property.getConfig(), "BatchConsumeBlockingQueueTriggerConfig must not be null");
        return new BatchConsumerTriggerBuilder<DataExchange<T,R>>()
                .batchSize(property.getConfig().getBatchSize())
                .bufferSize(property.getConfig().getBufferSize())
                .linger(Duration.ofMillis(property.getConfig().getBatchConsumeIntervalMills()));

    }

    @Override
    public boolean support(String bufferTriggerBizType) {
        if(StrUtil.isNotBlank(property.getBufferTriggerBizType()) && property.getBufferTriggerBizType().equals(bufferTriggerBizType)){
            return true;
        }
        return false;
    }
}
