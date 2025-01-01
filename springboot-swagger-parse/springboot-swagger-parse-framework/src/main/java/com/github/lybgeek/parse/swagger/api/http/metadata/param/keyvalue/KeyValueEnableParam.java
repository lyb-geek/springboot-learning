package com.github.lybgeek.parse.swagger.api.http.metadata.param.keyvalue;

import lombok.Data;

/**
 * @description:可以启用禁用的键值对参数
 *
 **/
@Data
public class KeyValueEnableParam extends KeyValueParam {
    /**
     * 是否启用
     * 默认启用
     */
    private Boolean enable = true;
    /**
     * 描述
     */
    private String description;
}
