package com.github.lybgeek.kafka.producer.service;



import com.github.lybgeek.kafka.model.ParamRequest;

import java.util.concurrent.ExecutionException;

public interface KafkaService {

    String sendAndReceive(String topic, ParamRequest request) throws ExecutionException, InterruptedException;

}
