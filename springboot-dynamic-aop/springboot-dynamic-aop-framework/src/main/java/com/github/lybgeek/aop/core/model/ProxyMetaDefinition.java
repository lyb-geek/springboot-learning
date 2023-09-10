package com.github.lybgeek.aop.core.model;


import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProxyMetaDefinition {

    private String id;

    private String proxyUrl;

    private String proxyClassName;

    private String pointcut;


    public static void main(String[] args) {
        ProxyMetaDefinition proxyMetaInfo = ProxyMetaDefinition.builder()
//                .proxyUrl("http://localhost:8081/repository/maven-snapshots/com/github/lybgeek/springboot-aop-plugin-timecost/1.0-SNAPSHOT/springboot-aop-plugin-timecost-1.0-20230824.024753-1.jar")
                .proxyUrl("jar:file:F:/springboot-learning/springboot-dynamic-aop/springboot-aop-plugin/springboot-aop-plugin-timecost/target/springboot-aop-plugin-timecost-0.0.1-SNAPSHOT.jar!/")
                .proxyClassName("com.github.lybgeek.interceptor.TimeCostMethodInterceptor")
                .pointcut("execution (* com.github.lybgeek.aop.test.hello.service.HelloService.*(..))")
                .id("timeCostEchoService")
                .build();
        System.out.println(JSONUtil.toJsonStr(proxyMetaInfo));

    }

}
