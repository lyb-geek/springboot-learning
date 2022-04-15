package com.github.lybgeek.mq.kafka.comsume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaComsumePayLoad implements Serializable {
    /**
     * 业务数据
     */
    private String data;
    private String receivedTopic;
    private String receivedMessageKey;
    private long receivedTimestamp;

}
