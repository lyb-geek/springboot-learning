package com.github.lybgeek.kafka.consumer.service;


import com.github.lybgeek.kafka.consumer.model.Request;
import com.github.lybgeek.kafka.model.ParamRequest;

public interface HttpTemplate {

    Object get(Request request);

    Object post(Request request);

    Object postJson(Request request);
}
