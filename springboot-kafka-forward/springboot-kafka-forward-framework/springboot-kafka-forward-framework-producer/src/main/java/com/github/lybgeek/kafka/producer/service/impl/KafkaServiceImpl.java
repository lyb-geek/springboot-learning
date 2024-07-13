package com.github.lybgeek.kafka.producer.service.impl;


import cn.hutool.json.JSONUtil;
import com.github.lybgeek.kafka.constant.KafkaConstant;
import com.github.lybgeek.kafka.model.ParamRequest;
import com.github.lybgeek.kafka.producer.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;

@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    /**
     * 作用：ReplyingKafkaTemplate 是 Spring Kafka 提供的一个类，专门用于处理 Kafka 中的请求/响应模式。它允许你发送一个消息到 Kafka，并等待一个响应。这对于需要从服务端接收响应的场景非常有用。
     * 使用场景：当你需要客户端发送一条消息并期望从服务端获得特定响应时，例如查询操作或远程调用。
     * 特点：它内部管理了发送和接收消息的逻辑，包括使用临时队列来接收响应消息，以及处理消息的异步特性。
     * 配置：需要配置生产者工厂和消费者工厂，并且在发送消息时需要指定响应主题或者在消息头中设置。
     */
    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;


    /**
     *
     * @param topic
     * @param request
     * @return
     */
    @SneakyThrows
    @Override
    public String sendAndReceive(String topic, ParamRequest request)  {
        // 创建ProducerRecord类，用来发送消息
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>(topic, JSONUtil.toJsonStr(request));
        // 添加KafkaHeaders.REPLY_TOPIC到record的headers参数中，这个参数配置我们想要转发到哪个Topic中
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, KafkaConstant.REPLY_TOPIC.getBytes()));
        // sendAndReceive方法返回一个Future类RequestReplyFuture，
        // 这里类里面包含了获取发送结果的Future类和获取返回结果的Future类。
        // 使用replyingKafkaTemplate发送及返回都是异步操作
        RequestReplyFuture<String, String, String> replyFuture = replyingKafkaTemplate.sendAndReceive(producerRecord);
        // 获取发送结果
        SendResult<String, String> sendResult = replyFuture.getSendFuture().get();
        log.info("send message success,topic:{},message:{},sendResult:{}",topic,JSONUtil.toJsonStr(request),sendResult.getRecordMetadata());
        // 获取响应结果
        ConsumerRecord<String, String> consumerRecord = replyFuture.get();
        String result = consumerRecord.value();

        log.info("result: {}",result);
        return result;

    }
}
