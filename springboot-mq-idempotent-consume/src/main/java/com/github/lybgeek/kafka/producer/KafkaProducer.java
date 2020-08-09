package com.github.lybgeek.kafka.producer;


import com.github.lybgeek.kafka.constant.Constant;
import com.github.lybgeek.kafka.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class KafkaProducer implements CommandLineRunner {


    @Autowired
    private KafkaTemplate kafkaTemplate;

    private int threadNum = 2;

    private ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

    private CountDownLatch countDownLatch = new CountDownLatch(threadNum);


    @Override
    public void run(String... args) throws Exception {
          send();
    }


    private void send(){
        for(int i = 0; i < threadNum; i++){
            executorService.submit(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                   log.error(e.getMessage(),e);
                }
                String messageId = "b14701b8-4b08-4bbd-8a4e-70f76a432e99";

                MessageDTO messageDTO = MessageDTO.builder().messageId(messageId).data("hello").build();
                kafkaTemplate.send(Constant.TOPIC,messageDTO);
            });

            countDownLatch.countDown();
        }

    }
}
