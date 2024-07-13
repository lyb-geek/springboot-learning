package com.github.lybgeek.kafka.constant;


public interface KafkaConstant {
    String DEFAULT_TOPIC = "test";
    String DEFAULT_TOPIC_PATTERN = "${lybgeek.consumer.topic:test}";
    String TOPIC = "lybgeek-kafka-topic-" + DEFAULT_TOPIC_PATTERN;
    String TOPIC_PARAM_KEY = "topic";
    String REPLY_TOPIC = "lybgeek-kafka-reply-topic";
}
