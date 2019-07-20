package com.github.lybgeek.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.lybgeek.template.DbTemplate;
import com.github.lybgeek.util.DruidDataSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class DbTemplateConfig {


  @Bean
  public DbTemplate dbTemplate(DbConfig dbConfig){
    DbTemplate dbTemplate = new DbTemplate(new QueryRunner(),dataSource(dbConfig));
    return dbTemplate;
  }

  @Bean
  @Primary
  public DruidDataSource dataSource(DbConfig dbConfig){
     return DruidDataSourceUtil.INSTANCE.getDataSource(dbConfig);
  }

}
