package com.github.lybgeek.parse.swagger.api.http.metadata.param;


import com.github.lybgeek.parse.swagger.enums.ValueEnum;

/**
 * @description:请求体键值参数的参数类型
 *
 **/
public enum BodyParamType implements ValueEnum {
    /**
     * 字符串类型
     * 默认 application/text
     */
    STRING("string"),
    /**
     * 整型
     * 默认 application/text
     */
    INTEGER("integer"),
    /**
     * 数值型
     * 默认 application/text
     */
    NUMBER("number"),
    BOOLEAN("boolean"),
    /**
     * 数组
     * 默认 application/text
     */
    ARRAY("array"),
    /**
     * 文件类型
     * 默认 application/octet-stream
     */
    FILE("file"),
    /**
     * json 类型
     * 默认 application/json
     */
    JSON("json");

    private String value;

    BodyParamType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
