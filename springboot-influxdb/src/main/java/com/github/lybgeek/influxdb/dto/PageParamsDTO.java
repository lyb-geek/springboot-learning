package com.github.lybgeek.influxdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class PageParamsDTO extends QueryParamsDTO{

    /**
     * 分页大小
     */
    private int pageSize = 10;

    /**
     * 页码默认为1
     */
    private int pageNo = 1;
}
