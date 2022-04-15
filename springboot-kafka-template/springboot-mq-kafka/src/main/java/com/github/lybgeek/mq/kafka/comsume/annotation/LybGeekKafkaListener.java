package com.github.lybgeek.mq.kafka.comsume.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@KafkaListener
@Documented
@Component
public @interface LybGeekKafkaListener {

    @AliasFor(annotation = KafkaListener.class, attribute = "id")
    String id() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "containerFactory")
    String containerFactory() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "topics")
    String[] topics() default {};


    @AliasFor(annotation = KafkaListener.class, attribute = "topicPattern")
    String topicPattern() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "topicPartitions")
    TopicPartition[] topicPartitions() default {};


    @AliasFor(annotation = KafkaListener.class, attribute = "containerGroup")
    String containerGroup() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "errorHandler")
    String errorHandler() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "groupId")
    String groupId() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "idIsGroup")
    boolean idIsGroup() default true;


    @AliasFor(annotation = KafkaListener.class, attribute = "clientIdPrefix")
    String clientIdPrefix() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "beanRef")
    String beanRef() default "__listener";


    @AliasFor(annotation = KafkaListener.class, attribute = "concurrency")
    String concurrency() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "autoStartup")
    String autoStartup() default "";


    @AliasFor(annotation = KafkaListener.class, attribute = "properties")
    String[] properties() default {};


    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

}
