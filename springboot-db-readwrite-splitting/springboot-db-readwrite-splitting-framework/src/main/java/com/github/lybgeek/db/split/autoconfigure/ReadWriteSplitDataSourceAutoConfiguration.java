package com.github.lybgeek.db.split.autoconfigure;


import com.github.lybgeek.db.split.enums.DataSourceTypeEnum;
import com.github.lybgeek.db.split.interceptor.DataSourceSwitchInterceptor;
import com.github.lybgeek.db.split.route.DynamicRoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "spring.datasource.dynamic.enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class ReadWriteSplitDataSourceAutoConfiguration {

    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @ConditionalOnProperty(name = "spring.datasource.master.jdbc-url")
    public DataSource masterDataSource(){
      return DataSourceBuilder.create().build();
    }

    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    @ConditionalOnProperty(name = "spring.datasource.slave.jdbc-url")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("dataSource")
    @Primary
    public DataSource dynamicDataSource( DataSource masterDataSource, DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DataSourceTypeEnum.SLAVE, slaveDataSource);

        DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }


    @Bean
    @ConditionalOnMissingBean
    public DataSourceSwitchInterceptor dataSourceSwitchInterceptor(){
        return new DataSourceSwitchInterceptor();
    }
}
