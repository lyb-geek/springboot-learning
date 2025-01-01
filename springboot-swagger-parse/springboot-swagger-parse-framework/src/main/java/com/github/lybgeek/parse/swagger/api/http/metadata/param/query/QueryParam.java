package com.github.lybgeek.parse.swagger.api.http.metadata.param.query;

import com.github.lybgeek.parse.swagger.api.http.metadata.param.keyvalue.KeyValueEnableParam;
import com.github.lybgeek.parse.swagger.enums.KeyValueParamType;
import lombok.Data;

/**
 * @description:query 参数
 * @param:
 * @return:
 **/
@Data
public class QueryParam extends KeyValueEnableParam {

    /**
     * 参数类型
     * 取值参考 {@link KeyValueParamType}
     * 默认String
     */
    private String paramType = KeyValueParamType.STRING.getValue();
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
     * 默认 false
     */
    private Boolean encode = false;
}
