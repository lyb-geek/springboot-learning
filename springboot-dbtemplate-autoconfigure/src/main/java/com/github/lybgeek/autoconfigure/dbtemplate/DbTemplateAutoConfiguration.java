package com.github.lybgeek.autoconfigure.dbtemplate;

import com.github.lybgeek.db.template.DbTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
@EnableConfigurationProperties(DbProperties.class)
@ConditionalOnProperty(name="db.db-template-enabled",havingValue = "true")
public class DbTemplateAutoConfiguration {

    @Autowired
    private DbProperties dbProperties;

    @Bean
    @ConditionalOnMissingBean(value= DbTemplate.class)
    public DbTemplate dbTemplate(){
        DbTemplate dbTemplate = new DbTemplate(new QueryRunner(),dataSource());
        return dbTemplate;
    }


    @Bean
    @ConditionalOnMissingBean(value= DataSource.class)
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbProperties.getDriverClassName());
        dataSource.setUrl(dbProperties.getUrl());
        dataSource.setUsername(dbProperties.getUsername());
        dataSource.setPassword(dbProperties.getPassword());
        return dataSource;
    }

}
