package com.github.lybgeek.autoid.process;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;


public abstract class BaseAutoIdProcess {

    protected Field field;
    /**
     * 主键名
     */
    protected String primaryKey;

    public BaseAutoIdProcess(Field field){
        this.field = field;
    }

    abstract void setFieldValue(Object entity) throws Exception;

    /**
     * 判断id字段是否已经有值，如果不存在，则进行设值
     * @param entity
     * @return
     */
    private boolean isNotExistFieldValue(Object entity) throws Exception {
        field.setAccessible(true);
        Object value = field.get(entity);
        return ObjectUtils.isEmpty(value);
    }


    public void process(Object entity) throws Exception{
        if(isNotExistFieldValue(entity)){
            setFieldValue(entity);
        }
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
