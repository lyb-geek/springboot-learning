package com.github.lybgeek.influxdb.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;


import com.github.lybgeek.influxdb.autoconfigure.CustomInfluxDbProperties;
import com.github.lybgeek.influxdb.dto.PageParamsDTO;
import com.github.lybgeek.influxdb.dto.QueryParamsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class InfluxdbHelpler {

    private InfluxDB influxDB;

    private CustomInfluxDbProperties influxDbProperties;

    public InfluxdbHelpler(InfluxDB influxDB, CustomInfluxDbProperties influxDbProperties) {
        this.influxDB = influxDB;
        this.influxDbProperties = influxDbProperties;
    }


    public <T> boolean save(T entity){
        if (validate(entity)) {
            return false;
        }
        try {
            BatchPoints batchPoints = BatchPoints.database(influxDbProperties.getDatasource())
                    // 一致性
                    .consistency(InfluxDB.ConsistencyLevel.ALL).build();
            Point.Builder builder = buildPoint(entity);
            batchPoints.point(builder.build());
            influxDB.write(batchPoints);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return false;

    }


    public <T> boolean saveBatch(List<T> entityList){
        if(CollectionUtils.isEmpty(entityList)){
            log.warn("entityList is empty");
            return false;


        }

        if (validate(entityList.get(0))) {
            return false;
        }

        try {
            BatchPoints batchPoints = BatchPoints.database(influxDbProperties.getDatasource())
                    // 一致性
                    .consistency(InfluxDB.ConsistencyLevel.ALL).build();
            for (T entity : entityList) {
                Point.Builder builder = buildPoint(entity);
                batchPoints.point(builder.build());
            }
            influxDB.write(batchPoints);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return false;

    }

    public <T> List<T> query(QueryParamsDTO queryParamsDTO, Class<?> entityClass){
        String query = buildQuerySql(queryParamsDTO, entityClass);
        return query(query,entityClass);
    }

    /**
     * 构造查询sql
     * @param queryParamsDTO
     * @param entityClass
     * @return
     */
    private String buildQuerySql(QueryParamsDTO queryParamsDTO, Class<?> entityClass) {
        StringBuilder query = new StringBuilder();
        String fields = "*";
        Measurement measurement = getMeasurement(entityClass);
        if(CollectionUtil.isNotEmpty(queryParamsDTO.getFieldKeys())){
            fields = StringUtils.join(queryParamsDTO.getFieldKeys(),",");
        }
        query.append("select ").append(fields).append(" from ").append(measurement.name());

        if(StringUtils.isNotBlank(queryParamsDTO.getQueryCondition())){
            query.append(" where ").append(queryParamsDTO.getQueryCondition());
        }

        if(StringUtils.isNotBlank(queryParamsDTO.getOrderRules())){
            query.append(" order by ").append(queryParamsDTO.getOrderRules());
        }

        if(queryParamsDTO instanceof PageParamsDTO){
            PageParamsDTO pageParamsDTO = (PageParamsDTO)queryParamsDTO;
            if(pageParamsDTO.getPageSize() > 0 && pageParamsDTO.getPageNo() > 0){
                String pageQuery = " LIMIT " + pageParamsDTO.getPageSize() + " OFFSET " + (pageParamsDTO.getPageNo() - 1) * pageParamsDTO.getPageSize();
                query.append(pageQuery);
            }

        }
        log.info("buildQuerySql:{}",query.toString());
        return query.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> List<T> query(String query, Class<?> entityClass){
        List results = new ArrayList<>();
        QueryResult queryResult = influxDB.query(new Query(query, influxDbProperties.getDatasource()));
        if(CollectionUtil.isNotEmpty(queryResult.getResults())){
            queryResult.getResults().forEach(result->{
                result.getSeries().forEach(serial->{
                    List<String> columns = serial.getColumns();
                    int fieldSize = columns.size();
                    serial.getValues().forEach(value->{
                        Object obj = fillField4Entity(entityClass, columns, fieldSize, value);
                        results.add(obj);
                    });
                });
            });
        }

        return results;
    }

    /**
     * 属性值填充
     * @param entityClass
     * @param columns
     * @param fieldSize
     * @param value
     * @return
     */
    private Object fillField4Entity(Class<?> entityClass, List<String> columns, int fieldSize, List<Object> value) {
        Object obj = null;
        try {
            obj = entityClass.newInstance();
            for(int i = 0;i < fieldSize; i++){
                String fieldName = columns.get(i);
                Field field = entityClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Class<?> type = field.getType();
                fillField(value, obj, i, field, type);
            }
        } catch (NoSuchFieldException | SecurityException | InstantiationException | IllegalAccessException e) {
            log.error("query fail:" + e.getMessage(),e);
        }
        return obj;
    }

    private void fillField(List<Object> value, Object obj, int i, Field field, Class<?> type) throws IllegalAccessException {
        if(type.isAssignableFrom(Float.class)){
            field.set(obj, Float.valueOf(value.get(i).toString()));
        }else if(type.isAssignableFrom(Integer.class)){
            field.set(obj, Integer.valueOf(value.get(i).toString()));
        }else if(type.isAssignableFrom(Byte.class)) {
            field.set(obj, Byte.valueOf(value.get(i).toString()));
        }else if(type.isAssignableFrom(Double.class)){
            field.set(obj, Double.valueOf(value.get(i).toString()));
        }else if(type.isAssignableFrom(Long.class)){
            field.set(obj, Long.valueOf(value.get(i).toString()));
        }else if(type.isAssignableFrom(Short.class)){
            field.set(obj, Short.valueOf(value.get(i).toString()));
        }else if(type.isAssignableFrom(Boolean.class)){
            field.set(obj, Boolean.valueOf(value.get(i).toString()));
        }else{
            field.set(obj,value.get(i));
        }
    }


    private <T> boolean validate(T entity) {
        Measurement measurement = getMeasurement(entity.getClass());
        if(ObjectUtil.isNull(measurement)){
            return true;
        }
        return false;
    }

    /**
     * 构造point，类比数据库的行记录
     * @param entity
     * @param <T>
     * @return
     */
    private <T> Point.Builder buildPoint(T entity) {
        Measurement measurement = getMeasurement(entity.getClass());
        Point.Builder builder = Point.measurement(measurement.name());
        Field[] fields = ReflectUtil.getFields(entity.getClass());
        for (Field field : fields) {
            if(!field.isAnnotationPresent(Column.class)){
                continue;
            }
            try {
                Column column = field.getAnnotation(Column.class);
                //设置属性可操作
                field.setAccessible(true);
                if(column.tag()){
                    //tag属性只能存储String类型
                    builder.tag(column.name(), field.get(entity).toString());
                }else{
                    //设置field
                    if(field.get(entity) != null){
                        builder.addField(column.name(), field.get(entity).toString());
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
               log.error(e.getMessage(),e);
            }
        }
        return builder;
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }

    public <T> Measurement getMeasurement(Class<T> entityClass){
        if(!entityClass.isAnnotationPresent(Measurement.class)){
            log.error("@Measurement must be annotation on {}",entityClass.getName());
            return null;
        }

        return entityClass.getAnnotation(Measurement.class);
    }
}
