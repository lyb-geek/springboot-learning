package com.github.lybgeek.parse.swagger.enums;

/**
 * 键值对参数的参数类型
 * rest 参数和 query 参数
 */
public enum KeyValueParamType implements ValueEnum {
    /**
     * 字符串类型
     */
    STRING("string"),
    /**
     * 整型
     */
    INTEGER("integer"),
    /**
     * 数值型
     */
    NUMBER("number"),
    /**
     * 布尔类型
     */
    BOOLEAN("boolean"),
    /**
     * 数组
     */
    ARRAY("array");

    private String value;

    KeyValueParamType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
