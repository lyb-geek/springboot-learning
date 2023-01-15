package com.github.lybgeek.jackson.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = CustomJacksonFormatProperties.PREFIX)
public class CustomJacksonFormatProperties {

    public static final String PREFIX = "lybgeek.json.format";

    public static final String CUSTOM_JSON_FORMAT_ENABLE_KEY = PREFIX + ".enabled";


    private boolean enabled = true;



}