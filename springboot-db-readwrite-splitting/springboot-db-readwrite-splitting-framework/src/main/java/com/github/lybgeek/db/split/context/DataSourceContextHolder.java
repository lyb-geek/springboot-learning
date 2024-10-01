package com.github.lybgeek.db.split.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.github.lybgeek.db.split.enums.DataSourceTypeEnum;

public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceTypeEnum> contextHolder = new TransmittableThreadLocal<>();

    public static void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static DataSourceTypeEnum getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}