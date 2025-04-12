package com.github.lybgeek.sse.service.support;

import com.github.lybgeek.sse.service.SsePublisherService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

/**
 * UnicastProcessor 只允许有一个订阅者，若有多个订阅者，会抛出异常。适用于仅需一个订阅者的场景，像单个客户端接收实时数据更新的情况。
 */
public class UnicastProcessorSsePublisherService implements SsePublisherService {

    private final UnicastProcessor<String> processor = UnicastProcessor.create();
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