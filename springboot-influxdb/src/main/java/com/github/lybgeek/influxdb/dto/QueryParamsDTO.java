package com.github.lybgeek.influxdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class QueryParamsDTO {

    /**
     * 要查询的的字段值，如果为空，默认查询所有
     */
    private List<String> fieldKeys;

    /**
     * 排序规则,形如 按时时间降序：time desc,如果多个排序规则,按,切割，形如time desc,stock desc
     */
    private String orderRules;

    /**
     * 查询条件，需自行拼凑查询条件，比如 stock = '1'
     * (注意查询条件选择tag值,选择field数值会严重拖慢查询速度)
     */
    private String queryCondition;


}
