package com.github.lybgeek.mq.kafka.comsume.multi.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = MultiKafkaComsumeProperties.PREFIX)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiKafkaComsumeProperties {

    public static final String PREFIX = "lybgeek.kafka.multi";

    private boolean comsumeEnabled = false;
}
