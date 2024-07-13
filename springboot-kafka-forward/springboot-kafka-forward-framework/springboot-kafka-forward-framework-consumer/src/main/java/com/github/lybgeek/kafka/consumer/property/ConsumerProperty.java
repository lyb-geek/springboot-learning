package com.github.lybgeek.kafka.consumer.property;

import cn.hutool.core.net.NetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = ConsumerProperty.PREFIX)
public class ConsumerProperty implements ApplicationListener<WebServerInitializedEvent> {

    public final static String PREFIX = "lybgeek.consumer";

    private String topic;

    private String baseUrl;

    private String groupIdPrefix;

    private String scheme = "http";

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        if(StringUtils.isEmpty(baseUrl)){
            this.baseUrl = scheme + "://" + NetUtil.getLocalhostStr() + ":" + event.getWebServer().getPort();
        }
    }
}
