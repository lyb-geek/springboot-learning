package com.github.lybgeek.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页结果.
 *
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PageResult<T> {

    /**
     * 页码，从1开始
     */
    private Integer pageNo;

    /**
     * 页面大小
     */
    private Integer pageSize;


    /**
     * 总数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 数据
     */
    private List<T> list;

}
