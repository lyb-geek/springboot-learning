package com.github.lybgeek.parse.swagger.api.http.metadata.param.keyvalue;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;
import java.util.Map;

@Data
public class KeyValueMatchRule {
    @ApiModelProperty(value = "是否是全部匹配 （false为任意匹配）")
    private boolean isMatchAll;
    @ApiModelProperty(value = "匹配规则")
    private List<KeyValueInfo> matchRules;

    public boolean match(Map<String, String> matchParam, boolean isHeader) {
        if (!isHeader && ((MapUtil.isEmpty(matchParam) && CollectionUtil.isNotEmpty(matchRules)) ||
                (CollectionUtil.isEmpty(matchRules) && MapUtil.isNotEmpty(matchParam)))) {
            return false;
        }
        if (isMatchAll) {
            for (KeyValueInfo matchRule : matchRules) {
                if (!matchRule.matchValue(matchParam.get(matchRule.getKey()))) {
                    return false;
                }
            }
            return true;
        } else {
            for (KeyValueInfo matchRule : matchRules) {
                if (matchRule.matchValue(matchParam.get(matchRule.getKey()))) {
                    return true;
                }
            }
            return false;
        }
    }
}
