package com.github.lybgeek.check.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;

import com.github.lybgeek.check.callback.DBCommunicationsCallBack;
import com.github.lybgeek.check.callback.support.DefaultCommunicationsCallBack;
import com.github.lybgeek.check.core.DbConnManger;
import com.github.lybgeek.check.core.service.DbCheckService;
import com.github.lybgeek.check.core.valid.ValidConnectionChecker;
import com.github.lybgeek.check.core.valid.support.ValidConnectionCheckerAdapter;
import com.github.lybgeek.check.event.listener.CommunicationsHealthEventListener;
import com.github.lybgeek.check.event.listener.CommunicationsUnHealthEventListener;
import com.github.lybgeek.check.properties.DbCheckProperies;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

import static com.github.lybgeek.check.properties.DbCheckProperies.PREFIX;


/**
 * @description: db检测自动装配
 **/
@Configuration
@Import(SpringUtil.class)
@EnableConfigurationProperties(DbCheckProperies.class)
@ConditionalOnClass(DataSource.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = PREFIX,name = "enabled",havingValue = "true",matchIfMissing = true)
public class DbCheckAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DBCommunicationsCallBack dbCommunicationsCallBack(){
        return new DefaultCommunicationsCallBack();
    }



    @Bean
    @ConditionalOnMissingBean
    public CommunicationsUnHealthEventListener communicationsUnHealthEventListener(){
        return new CommunicationsUnHealthEventListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommunicationsHealthEventListener communicationsHealthEventListener(){
        return new CommunicationsHealthEventListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidConnectionChecker validConnectionChecker(DbCheckProperies dbCheckProperies){
        return new ValidConnectionCheckerAdapter(dbCheckProperies);
    }

    @Bean
    @ConditionalOnMissingBean
    public DbConnManger dbConnManger(DataSourceProperties dataSourceProperties){
        return new DbConnManger(dataSourceProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DbCheckService dbCheckService(DbCheckProperies dbCheckProperies,ValidConnectionChecker validConnectionChecker,DbConnManger dbConnManger){
        return new DbCheckService(validConnectionChecker,dbCheckProperies,dbConnManger);
    }
}
