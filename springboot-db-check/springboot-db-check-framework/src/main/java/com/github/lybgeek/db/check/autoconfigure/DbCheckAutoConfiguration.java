package com.github.lybgeek.db.check.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import com.github.lybgeek.db.check.callback.DBCommunicationsCallBack;
import com.github.lybgeek.db.check.callback.support.DefaultCommunicationsCallBack;
import com.github.lybgeek.db.check.event.listener.CommunicationsHealthEventListener;
import com.github.lybgeek.db.check.event.listener.CommunicationsUnHealthEventListener;
import com.github.lybgeek.db.check.processor.DruidDataSourceBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description: db检测自动装配
 **/
@Configuration
@Import(SpringUtil.class)
public class DbCheckAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DBCommunicationsCallBack dbCommunicationsCallBack(){
        return new DefaultCommunicationsCallBack();
    }

    @Bean
    @ConditionalOnMissingBean
    public static DruidDataSourceBeanPostProcessor druidDataSourceBeanPostProcessor(DataSourceProperties dataSourceProperties){
        return new DruidDataSourceBeanPostProcessor(dataSourceProperties);
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
}
