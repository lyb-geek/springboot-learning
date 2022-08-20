package com.github.lybgeek.event.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = LybGeekApplicationEventProperties.PREFIX)
public class LybGeekApplicationEventProperties {

    public final static String PREFIX = "lybgeek.event";

    /**
     * 是否开启异步，默认为true
     */
    public boolean async = true;
}
