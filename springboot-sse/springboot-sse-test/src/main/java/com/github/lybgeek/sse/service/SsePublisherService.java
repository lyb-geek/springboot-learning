package com.github.lybgeek.sse.service;


import reactor.core.publisher.Flux;

public interface SsePublisherService {

     Flux<String> getMessages();

     void publishMessage(String message);

     void complete();
}
