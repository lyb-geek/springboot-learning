package com.github.lybgeek.aop.core.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProxyMetaInfo {

    private String id;

    private String proxyUrl;

    private String proxyClassName;

    private Object target;

    private String pointcut;

}
