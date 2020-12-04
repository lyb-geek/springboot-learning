package com.github.lybgeek.kafka.producer.interceptor;

import com.alibaba.fastjson.JSON;
import com.github.lybgeek.kafka.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

import static com.github.lybgeek.kafka.constant.Constant.TOPIC_KEY_PREFIX;



@Slf4j
public class KafkaProducerInterceptor implements ProducerInterceptor<String, MessageDTO> {



    /**
     * 运行在用户主线程中，在消息被序列化之前调用
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, MessageDTO> onSend(ProducerRecord<String, MessageDTO> record) {
        log.info("onSend,orginalRecord:{}", JSON.toJSONString(record));
        return new ProducerRecord<String, MessageDTO>(TOPIC_KEY_PREFIX + record.topic(),
                record.partition(),record.timestamp(),record.key(), record.value());
    }




    /**
     * 在消息被应答之前或者消息发送失败时调用，通常在producer回调逻辑触发之前，运行在produer的io线程中
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }


    /**
     *  清理工作
     */
    @Override
    public void close() {
    }


    /**
     * 初始化工作
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {

    }
}