package com.github.lybgeek.parse.swagger.api.http.metadata.body.form;

import com.github.lybgeek.parse.swagger.api.http.metadata.param.BodyParamType;
import com.github.lybgeek.parse.swagger.api.http.metadata.param.keyvalue.KeyValueEnableParam;
import lombok.Data;

/**
 * x-www-form-urlencoded 请求体键值对
 */
@Data
public class WWWFormKV extends KeyValueEnableParam {
    /**
     * 参数类型
     * 取值参考 {@link BodyParamType} 中的 value 属性
     */
    private String paramType = BodyParamType.STRING.getValue();
    /**
     * 是否必填
     * 默认为 false
     */
    private Boolean required = false;
    /**
     * 最小长度
     */
    private Integer minLength;
    /**
     * 最大长度
     */
    private Integer maxLength;
}
