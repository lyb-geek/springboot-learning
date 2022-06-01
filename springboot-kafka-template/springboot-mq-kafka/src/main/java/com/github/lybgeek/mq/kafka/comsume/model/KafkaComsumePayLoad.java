package com.github.lybgeek.mq.kafka.comsume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: Linyb
 * @date : 2022/2/23 16:10
 **/
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
