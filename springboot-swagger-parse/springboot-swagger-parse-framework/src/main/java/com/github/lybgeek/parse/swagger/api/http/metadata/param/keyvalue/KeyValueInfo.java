package com.github.lybgeek.parse.swagger.api.http.metadata.param.keyvalue;

import com.github.lybgeek.parse.swagger.enums.ParamConditionEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class KeyValueInfo {
    @ApiModelProperty(value = "Key")
    private String key;
    @ApiModelProperty(value = "Value")
    private String value;
    /**
     * 默认等于，可选：等于、不等于、长度等于、长度大于、长度小于、包含、不包含、为空、非空、正则匹配
     * 文件类型的参数，等于、不等于、为空、非空
     */
    @ApiModelProperty(value = "条件")
    private String condition;
    @ApiModelProperty(value = "描述")
    private String description;

    public boolean matchValue(String value) {
        try {
            ParamConditionEnums conditionEnum = ParamConditionEnums.valueOf(this.condition);
            switch (conditionEnum) {
                case EQUALS:
                    return StringUtils.equals(this.value, value);
                case NOT_EQUALS:
                    return !StringUtils.equals(this.value, value);
                case CONTAINS:
                    return StringUtils.contains(value, this.value);
                case NOT_CONTAINS:
                    return !StringUtils.contains(value, this.value);
                case LENGTH_EQUALS:
                    return value.length() == Integer.parseInt(this.value);
                case LENGTH_NOT_EQUALS:
                    return value.length() != Integer.parseInt(this.value);
                case LENGTH_SHOT:
                    return value.length() < Integer.parseInt(this.value);
                case LENGTH_LARGE:
                    return value.length() > Integer.parseInt(this.value);
                case REGULAR_MATCH:
                    return value.matches(this.value);
                case IS_EMPTY:
                    return StringUtils.isBlank(value);
                case IS_NOT_EMPTY:
                    return StringUtils.isNotBlank(value);
                default:
                    throw new IllegalArgumentException("Unknown condition: " + conditionEnum);
            }
        } catch (Exception e) {
            return false;
        }

    }
}
