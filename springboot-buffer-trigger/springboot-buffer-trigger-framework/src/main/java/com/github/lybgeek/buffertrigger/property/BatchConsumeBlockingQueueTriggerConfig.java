package com.github.lybgeek.buffertrigger.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchConsumeBlockingQueueTriggerConfig {

    /**
     * 批处理元素的数量阈值，达到这个数量后也会进行消费
     */
    private int batchSize;

    /**
     * 批处理元素的数量阈值，达到这个数量后会进行消费
     */
    private int bufferSize;

    /**
     * 设置消息批处理延迟等待时间，单位毫秒。与{@link BatchConsumeBlockingQueueTriggerConfig#batchSize}结合.默认为5秒
     */
    private long batchConsumeIntervalMills = 5000;
}
