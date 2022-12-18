package com.github.lybgeek.http;


import com.github.lybgeek.http.common.HttpMethod;
import org.springframework.lang.Nullable;

import java.util.Map;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

public interface HttpTemplate {

    boolean supportsSourceType(@Nullable String sourceType);


    default int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    Object execute(String url, HttpMethod httpMethod,Map<String, String> headerMap, Map<String,Object> paramMap);
}
