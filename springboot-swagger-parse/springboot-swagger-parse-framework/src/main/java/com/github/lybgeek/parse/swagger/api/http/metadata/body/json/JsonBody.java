package com.github.lybgeek.parse.swagger.api.http.metadata.body.json;

import lombok.Data;


/**
 * @description:json 请求体
 **/
@Data
public class JsonBody {
    /**
     * 是否 json-schema
     * 默认false
     */
    private Boolean enableJsonSchema = false;
    /**
     * json 参数值
     */
    private String jsonValue;
    /**
     * json-schema 定义
     */
    private JsonSchemaItem jsonSchema;
}
