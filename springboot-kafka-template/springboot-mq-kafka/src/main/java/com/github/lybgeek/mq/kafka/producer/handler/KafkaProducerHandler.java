package com.github.lybgeek.mq.kafka.producer.handler;

import com.alibaba.fastjson.JSON;

import com.github.lybgeek.mq.handler.AbstractMqHandler;
import com.github.lybgeek.mq.model.MqReq;
import com.github.lybgeek.mq.model.MqResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;


@Slf4j
public class KafkaProducerHandler extends AbstractMqHandler {

    private KafkaTemplate kafkaTemplate;

    public KafkaProducerHandler(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public MqResp sendSync(MqReq mqReq) {
        ListenableFuture<SendResult<String, String>> result = this.send(mqReq);
        MqResp mqResp = this.buildMqResp(result);
        return mqResp;
    }


    @Override
    public void sendAsync(MqReq mqReq) {
        ListenableFuture<SendResult<String, String>> result = this.send(mqReq);
        this.recordExceptionLog(result);
    }

    public KafkaTemplate getKafkaTemplate() {
        return kafkaTemplate;
    }

    private ListenableFuture<SendResult<String, String>> send(MqReq mqReq) {
        String data = JSON.toJSONString(mqReq.getData());
        return kafkaTemplate.send(mqReq.getTopic(), data);
    }

    private MqResp buildMqResp(ListenableFuture<SendResult<String, String>> result) {
        MqResp mqResp = new MqResp();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        result.addCallback(new ListenableFutureCallback(){
            @Override
            public void onSuccess(Object result) {
                mqResp.setBody(JSON.toJSONString(result));
                mqResp.setOk(true);
                mqResp.setStatus(MqResp.SUCCESS);
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Throwable ex) {
                mqResp.setBody(ex.getMessage());
                mqResp.setOk(false);
                mqResp.setStatus(MqResp.FAIL);
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("{}",e);
        }
        return mqResp;
    }

    private void recordExceptionLog(ListenableFuture<SendResult<String, String>> result) {
        result.addCallback(new ListenableFutureCallback(){
            @Override
            public void onSuccess(Object result) {
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("send kafka mq error:{}",ex);
            }
        });
    }



}
