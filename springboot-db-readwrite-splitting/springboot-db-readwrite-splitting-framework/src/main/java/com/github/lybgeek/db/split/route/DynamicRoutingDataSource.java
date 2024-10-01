package com.github.lybgeek.db.split.route;


import com.github.lybgeek.db.split.context.DataSourceContextHolder;
import com.github.lybgeek.db.split.enums.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        if(DataSourceTypeEnum.SLAVE.equals(DataSourceContextHolder.getDataSourceType())) {
            log.info("DynamicRoutingDataSource 切换数据源到从库");
            return DataSourceTypeEnum.SLAVE;
        }
        log.info("DynamicRoutingDataSource 切换数据源到主库");
        // 根据需要指定当前使用的数据源，这里可以使用ThreadLocal或其他方式来决定使用主库还是从库
        return DataSourceTypeEnum.MASTER;
    }
}
