package com.github.lybgeek.sse.service.support;

import com.github.lybgeek.sse.service.SsePublisherService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class SinksSsePublisherService implements SsePublisherService {

    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public Flux<String> getMessages() {
        return sink.asFlux();
    }

    @Override
    public void publishMessage(String message) {
        sink.tryEmitNext(message);
    }

    @Override
    public void complete() {
        sink.tryEmitComplete();
    }
}