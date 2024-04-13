package com.github.lybgeek.buffertrigger.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchConsumeBlockingQueueTriggerProperty {

    private String bufferTriggerBizType;
    private BatchConsumeBlockingQueueTriggerConfig config;
}
