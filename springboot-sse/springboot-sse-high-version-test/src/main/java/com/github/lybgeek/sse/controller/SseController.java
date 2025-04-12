package com.github.lybgeek.sse.controller;

import com.github.lybgeek.sse.service.SsePublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/sse")
@Slf4j
public class SseController {

    @Autowired
    private SsePublisherService ssePublisherService;

    private final AtomicInteger counter = new AtomicInteger(0);

    @GetMapping(path = "/interval/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE+ "; charset=UTF-8")
    public Flux<ServerSentEvent<Integer>> streamSseMvc() {
        return Flux.interval(Duration.ofSeconds(1))
               .map(seq -> ServerSentEvent.<Integer>builder()
                       .data(counter.incrementAndGet())
                       .build()).takeUntil(event -> event.data() > 10).doOnComplete(() -> log.info("complete"));
    }




    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> subscribe() {
        return ssePublisherService.getMessages()
                .map(message -> ServerSentEvent.<String>builder()
                        .data(message)
                        .build());
    }

    @PostMapping("/publish")
    public void publish(@RequestBody String message) {
        ssePublisherService.publishMessage(message);
    }
}    