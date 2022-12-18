package com.github.lybgeek.http.common.properties;


import com.github.lybgeek.http.common.constant.HttpCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.lybgeek.http.common.properties.HttpProperties.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class HttpProperties {

    public static final String PREFIX = "lybgeek.http";

    private String httpType = HttpCommon.DEFAULT_HTTP_TYPE;

    private String clientId;

    private String secret;
}
