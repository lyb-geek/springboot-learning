package com.github.lybgeek.kafka.producer;


import com.github.lybgeek.mq.kafka.producer.handler.KafkaProducerHandler;
import com.github.lybgeek.mq.model.MqReq;
import com.github.lybgeek.user.constant.Constant;
import com.github.lybgeek.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserProducer implements CommandLineRunner {

    @Autowired
    private KafkaProducerHandler kafkaProducerHandler;

    @Value(Constant.USER_TOPIC)
    private String topic;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder().username("test")
                .email("test@qq.com")
                .fullname("test")
                .mobile("1350000000")
                .password("123456")
                .build();
        MqReq<User> mqReq = new MqReq<>(topic,user);
        kafkaProducerHandler.sendAsync(mqReq);
    }
}
