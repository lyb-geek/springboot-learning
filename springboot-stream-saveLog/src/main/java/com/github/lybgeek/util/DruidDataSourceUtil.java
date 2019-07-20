package com.github.lybgeek.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.lybgeek.config.DbConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public enum  DruidDataSourceUtil {
    INSTANCE;

  public DruidDataSource getDataSource(DbConfig dbConfig){
    DruidDataSource druidDataSource = new DruidDataSource();
    try {
      druidDataSource.setUsername(dbConfig.getUsername());
      druidDataSource.setPassword(dbConfig.getPassword());
      druidDataSource.setUrl(dbConfig.getUrl());
      druidDataSource.setMaxActive(dbConfig.getMaxActive());
      druidDataSource.setDriverClassName(dbConfig.getDriverClassName());
      druidDataSource.setInitialSize(dbConfig.getInitialSize());
      druidDataSource.setMaxWait(dbConfig.getMaxWait());
      druidDataSource.setFilters(dbConfig.getFilters());
      druidDataSource.setTimeBetweenConnectErrorMillis(dbConfig.getTimeBetweenEvictionRunsMillis());
      druidDataSource.setMinEvictableIdleTimeMillis(dbConfig.getMinEvictableIdleTimeMillis());
      druidDataSource.setValidationQuery(dbConfig.getValidationQuery());
      druidDataSource.setTestOnBorrow(dbConfig.isTestOnBorrow());
      druidDataSource.setTestWhileIdle(dbConfig.isTestWhileIdle());
      druidDataSource.setTestOnReturn(dbConfig.isTestOnReturn());
    } catch (SQLException e) {
      log.error("get dataSource error:"+e.getMessage(),e);
    }

    return druidDataSource;
  }
}
