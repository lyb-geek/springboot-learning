package com.github.lybgeek.sse.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = SsePublisherProperties.PREIFX)
public class SsePublisherProperties {

    public static final String PREIFX = "lybgeek.sse.publisher";

    private String type = "direct";
}
