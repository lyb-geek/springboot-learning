package com.github.lybgeek.parse.swagger.api.http.request;

import com.github.lybgeek.parse.swagger.api.http.config.HTTPAuthConfig;
import com.github.lybgeek.parse.swagger.api.http.config.HTTPConfig;
import com.github.lybgeek.parse.swagger.api.http.metadata.header.Header;
import com.github.lybgeek.parse.swagger.api.http.metadata.param.query.QueryParam;
import com.github.lybgeek.parse.swagger.api.http.metadata.param.rest.RestParam;
import lombok.Data;

import java.util.List;

/**
 * Http接口详情
 * <pre>
 * 其中包括：接口调试、接口定义、接口用例、场景的自定义请求 的详情
 * 接口协议插件的接口详情也类似
 * </pre>
 */
@Data
public class HttpRequest {

    /**
     * 组件标签名称
     */
    private String name;
    /**
     * 接口定义和用例的请求路径，或者完整路径
     */
    private String path;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求体
     */

    private RequestBody body;
    /**
     * 请求头
     */

    private List<Header> headers;
    /**
     * rest参数
     */

    private List<RestParam> rest;
    /**
     * query参数
     */

    private List<QueryParam> query;
    /**
     * 其他配置
     */

    private HTTPConfig otherConfig = new HTTPConfig();
    /**
     * 认证配置
     */

    private HTTPAuthConfig authConfig = new HTTPAuthConfig();
    /**
     * 模块ID
     * 运行时参数，接口无需设置
     */
    private String moduleId;

}