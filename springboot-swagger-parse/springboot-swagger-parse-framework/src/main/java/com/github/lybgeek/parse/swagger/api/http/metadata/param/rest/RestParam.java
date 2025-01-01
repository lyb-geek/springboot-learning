package com.github.lybgeek.parse.swagger.api.http.metadata.param.rest;

import com.github.lybgeek.parse.swagger.api.http.metadata.param.keyvalue.KeyValueEnableParam;
import lombok.Data;

/**
 * @description:rest参数
 **/
@Data
public class RestParam extends KeyValueEnableParam {
    /**
     * 参数类型
     * 默认String
     */
    private String paramType = "string";
    /**
     * 是否必填
     */
    private Boolean required = false;
    /**
     * 最大长度
     */
    private Integer minLength;
    /**
     * 最小长度
     */
    private Integer maxLength;
    /**
     * 是否编码
     */
    private Boolean encode = false;
}
