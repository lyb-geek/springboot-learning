package com.github.lybgeek.parse.swagger.api.http.config;

import lombok.Data;

/**
 * @description:http 的其他配置项
 **/
@Data
public class HTTPConfig {
    /**
     * 连接超时
     */
    private Long connectTimeout = 60000L;
    /**
     * 响应超时
     */
    private Long responseTimeout = 60000L;
    /**
     * 证书别名
     */
    private String certificateAlias;
    /**
     * 是否跟随重定向
     * 默认 true
     */
    private Boolean followRedirects = true;
    /**
     * 是否自动重定向
     * 默认 false
     */
    private Boolean autoRedirects = false;
}
