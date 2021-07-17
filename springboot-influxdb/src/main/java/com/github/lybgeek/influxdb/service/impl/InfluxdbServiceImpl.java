package com.github.lybgeek.influxdb.service.impl;

import cn.hutool.core.util.ObjectUtil;


import com.github.lybgeek.influxdb.dto.PageParamsDTO;
import com.github.lybgeek.influxdb.dto.QueryParamsDTO;
import com.github.lybgeek.influxdb.helper.InfluxdbHelpler;
import com.github.lybgeek.influxdb.service.InfluxdbService;
import com.github.lybgeek.influxdb.util.ReflectionUtils;
import org.influxdb.InfluxDB;
import org.influxdb.annotation.Measurement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:

 **/
public class InfluxdbServiceImpl<T> implements InfluxdbService<T> {

    @Autowired
    protected InfluxdbHelpler influxdbHelpler;

    protected InfluxDB getInfluxdb() {
        return influxdbHelpler.getInfluxDB();
    }

    protected Class<?> entityClass = currentModelClass();


    @Override
    public boolean save(T entity) {
        return influxdbHelpler.save(entity);
    }

    @Override
    public boolean saveBatch(List<T> entityList) {
        return influxdbHelpler.saveBatch(entityList);
    }

    @Override
    public List<T> query(String sql) {
        return influxdbHelpler.query(sql,entityClass);
    }

    @Override
    public List<T> query(QueryParamsDTO queryParamsDTO) {
        return influxdbHelpler.query(queryParamsDTO,entityClass);
    }

    @Override
    public List<T> page(PageParamsDTO pageParamsDTO) {
       return influxdbHelpler.query(pageParamsDTO,entityClass);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionUtils.getSuperClassGenericType(getClass(), 0);
    }

    protected String currentMeasurement() {
        Measurement measurement = influxdbHelpler.getMeasurement(entityClass);
        return ObjectUtil.isNotNull(measurement) ? measurement.name() : "";
    }
}
