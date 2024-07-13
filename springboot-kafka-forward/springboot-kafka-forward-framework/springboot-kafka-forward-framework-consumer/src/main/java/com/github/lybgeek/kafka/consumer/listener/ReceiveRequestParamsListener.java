package com.github.lybgeek.kafka.consumer.listener;


import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.github.lybgeek.kafka.consumer.model.Request;
import com.github.lybgeek.kafka.consumer.property.ConsumerProperty;
import com.github.lybgeek.kafka.consumer.service.HttpTemplate;
import com.github.lybgeek.kafka.model.ParamRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.SendTo;

import static com.github.lybgeek.kafka.constant.KafkaConstant.TOPIC;

@Slf4j
@RequiredArgsConstructor
public class ReceiveRequestParamsListener{

    private final ConsumerProperty consumerProperty;

    private final HttpTemplate httpTemplate;



    @KafkaListener(topics = TOPIC, groupId = "${lybgeek.consumer.group-id-prefix:lybgeek}-group-id", containerFactory = "kafkaListenerContainerFactory")
    /**
     * @SendTo 是一个Spring注解，常用于 Kafka 消费者方法之上，指示消息处理完成后应当将响应发送到哪个 Kafka 主题。
     * 使用场景：当你的应用作为服务端，需要对某个主题上的消息做出响应时，可以在处理该消息的方法上使用此注解来指定响应消息的目标主题。
     * 特点：简化了响应消息的路由配置，使得开发者无需显式地编写消息发送逻辑，只需关注业务处理逻辑。
     * 配合 ReplyingKafkaTemplate：在请求/响应模式中，@SendTo 指定的响应主题与 ReplyingKafkaTemplate 发送请求时设置的期望响应主题相匹配，从而使得请求方能够正确地接收响应消息。
     */
    //@SendTo("hello-test")
    @SendTo
    public String listen(String data, Acknowledgment ack) {
        log.info("receive data:{}",data);
        if(JSONUtil.isJson(data)){
            Object result = execute(JSONUtil.toBean(data, ParamRequest.class),ack);
            if(result != null){
                ack.acknowledge();
                return JSONUtil.toJsonStr(result);
            }
           ;
        }
        return null;
    }


//    @KafkaListener(topics = "hello-test", groupId = "${lybgeek.consumer.group-id-prefix:lybgeek}-group-id}"")
    public void receive(String data, Acknowledgment ack){
       log.info(">>>>>>>>>>>>data：{}",data);
       ack.acknowledge();
    }


    private Request buildRequest(ParamRequest paramRequest, Acknowledgment ack){
        String fullUrl = URLUtil.normalize( consumerProperty.getBaseUrl()+ "/" + paramRequest.getRequestUrl());
        return Request.builder()
                .paramRequest(paramRequest)
                .url(fullUrl)
                .build();
    }


    private Object execute(ParamRequest paramRequest, Acknowledgment ack){
        Request request = buildRequest(paramRequest,ack);
        switch (paramRequest.getHttpMethod()){
            case GET:
               return httpTemplate.get(request);
            case POST:
                return httpTemplate.post(request);
            case POST_JSON:
                return httpTemplate.postJson(request);
            default:
                break;

        }
        return null;
    }



}
