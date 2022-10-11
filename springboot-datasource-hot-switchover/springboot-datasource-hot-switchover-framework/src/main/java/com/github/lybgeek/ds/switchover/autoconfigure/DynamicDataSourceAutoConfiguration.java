package com.github.lybgeek.ds.switchover.autoconfigure;


import com.alibaba.druid.pool.DruidDataSource;

import com.github.lybgeek.ds.switchover.core.ds.DynamicDataSource;
import com.github.lybgeek.ds.switchover.core.manager.AbstractDataSourceManger;
import com.github.lybgeek.ds.switchover.core.manager.support.DruidDataSourceManger;
import com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Collections;

import static com.github.lybgeek.ds.switchover.core.ds.DynamicDataSource.DATASOURCE_KEY;


@Configuration
@EnableConfigurationProperties(BackupDataSourceProperties.class)
@ComponentScan(basePackages = "com.github.lybgeek.ds.switchover")
public class DynamicDataSourceAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    @Primary
    @ConditionalOnClass(DruidDataSource.class)
    public AbstractDataSourceManger abstractDataSourceManger(DataSourceProperties dataSourceProperties, BackupDataSourceProperties backupDataSourceProperties){
        return new DruidDataSourceManger(backupDataSourceProperties,dataSourceProperties);
    }

    @Bean("dataSource")
    @Primary
    @ConditionalOnBean(AbstractDataSourceManger.class)
    public DynamicDataSource dynamicDataSource(AbstractDataSourceManger abstractDataSourceManger) {
        DynamicDataSource source = new DynamicDataSource();
        DataSource dataSource = abstractDataSourceManger.createDataSource(false);
        source.setTargetDataSources(Collections.singletonMap(DATASOURCE_KEY, dataSource));
        return source;
    }










}
