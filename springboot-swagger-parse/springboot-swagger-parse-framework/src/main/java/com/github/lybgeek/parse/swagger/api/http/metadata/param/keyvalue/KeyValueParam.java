package com.github.lybgeek.parse.swagger.api.http.metadata.param.keyvalue;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class KeyValueParam {
    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;

    public boolean isValid() {
        return StringUtils.isNotBlank(key);
    }

    public boolean isNotBlankValue() {
        return StringUtils.isNotBlank(value);
    }
}
