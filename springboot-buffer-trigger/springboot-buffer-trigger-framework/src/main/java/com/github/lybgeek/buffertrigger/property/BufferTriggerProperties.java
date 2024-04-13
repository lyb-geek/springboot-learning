package com.github.lybgeek.buffertrigger.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = BufferTriggerProperties.PREFIX)
public class BufferTriggerProperties {
    public static final String PREFIX = "lybgeek.buffer.trigger";

    private List<BatchConsumeBlockingQueueTriggerProperty> consumeQueueTriggerProperties;

}
