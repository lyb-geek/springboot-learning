package com.github.lybgeek.circuitbreaker.fallback.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultFallBackResponse {

    public static final String DEFAULT_MSG = "too many requests to trigger a spi circuit break";

    public static final Integer TOO_MANY_REQUESTS_CODE = 429;

    private String msg = "";
    private Integer code;





}
