package com.github.lybgeek.desensitization.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = DesensitizedProperties.PREFIX)
public class DesensitizedProperties {

    public static final String PREFIX = "desensitized";

    public static final String STRATEGY = "strategy";

    public static final String ENABLED = "enabled";

    /**
     * 敏感词词库类路径，当类路径与词库目录同时存在时，则同时加载类路径和词库目录词库
     */
    private String filepath;

    /**
     * 敏感词词库目录，当类路径与词库目录同时存在时，则同时加载类路径和词库目录词库
     */
    private String dir;

    /**
     * 是否开启敏感词过滤，默认true
     */
    private boolean enabled = true;

    /**
     * 支持的注解实现策略: 基于json实现和mybatis实现，默认基于json实现
     */
    private String strategy = "json";
}
