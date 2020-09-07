package com.github.lybgeek.autoid.process;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.lang.reflect.Field;


public class SnowFlakeAutoIdProcess extends BaseAutoIdProcess {

    private static Snowflake snowflake = IdUtil.createSnowflake(0,0);


    public SnowFlakeAutoIdProcess(Field field) {
        super(field);
    }

    @Override
    void setFieldValue(Object entity) throws Exception{
        long value = snowflake.nextId();
        field.set(entity,value);
    }
}
