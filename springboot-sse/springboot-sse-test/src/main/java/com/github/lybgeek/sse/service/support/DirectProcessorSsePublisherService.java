package com.github.lybgeek.sse.service.support;

import com.github.lybgeek.sse.service.SsePublisherService;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

/**
 * DirectProcessor 允许有多个订阅者，当有新消息发布时，会把消息发送给所有订阅者。适合有多个订阅者的场景，比如多个客户端都要接收相同的实时消息。
 */
public class DirectProcessorSsePublisherService implements SsePublisherService {

    private final DirectProcessor<String> processor = DirectProcessor.create();
    private final Flux<String> flux = processor.replay().autoConnect();

    @Override
    public Flux<String> getMessages() {
        return flux;
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