package com.github.lybgeek.sse.service.support;

import com.github.lybgeek.sse.service.SsePublisherService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

/**
 * ReplayProcessor：适合需要向新订阅者重播之前消息的场景，例如新客户端连接时需要获取历史消息。
 */
public class ReplayProcessorSsePublisherService implements SsePublisherService {

    private final ReplayProcessor<String> processor = ReplayProcessor.create();

    @Override
    public Flux<String> getMessages() {
        return processor;
    }

    @Override
    public void publishMessage(String message) {
        processor.onNext(message);
    }

    @Override
    public void complete() {
        processor.onComplete();
    }
}    