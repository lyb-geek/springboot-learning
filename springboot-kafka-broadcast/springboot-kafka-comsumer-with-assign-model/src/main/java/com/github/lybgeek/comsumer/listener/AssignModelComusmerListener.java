package com.github.lybgeek.comsumer.listener;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class AssignModelComusmerListener implements ApplicationListener<WebServerInitializedEvent> {

    private Integer serverPort;

    @KafkaListener(topicPartitions =
            {@TopicPartition(topic = "${userCache.topic}", partitions = "0")})
    public void receive(Acknowledgment ack, ConsumerRecord record){
        System.out.println(String.format("serverPort:【%s】,接收到数据：【%s】",serverPort,record));
        ack.acknowledge();
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
}
