package com.github.lybgeek.influxdb.service;





import com.github.lybgeek.influxdb.dto.PageParamsDTO;
import com.github.lybgeek.influxdb.dto.QueryParamsDTO;

import java.util.List;

/**
 * @description: influxdb接口
 *
 **/
public interface InfluxdbService<T> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    boolean save(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    boolean saveBatch(List<T> entityList);

    /**
     * 查询
     *
     * @param sql sql语句
     */
    List<T> query(String sql);

    /**
     * 查询
     *
     * @param queryParamsDTO 查询DTO
     */
    List<T> query(QueryParamsDTO queryParamsDTO);

    /**
     * 查询分页
     *
     * @param pageParamsDTO 分页DTO
     */
    List<T> page(PageParamsDTO pageParamsDTO);
}
