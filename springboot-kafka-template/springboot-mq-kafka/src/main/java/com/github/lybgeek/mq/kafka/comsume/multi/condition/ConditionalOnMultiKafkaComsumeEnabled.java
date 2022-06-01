package com.github.lybgeek.mq.kafka.comsume.multi.condition;

import com.github.lybgeek.mq.kafka.comsume.multi.properties.MultiKafkaComsumeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;


@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = MultiKafkaComsumeProperties.PREFIX,name = "comsume-enabled",havingValue = "true")
public @interface ConditionalOnMultiKafkaComsumeEnabled {
}
