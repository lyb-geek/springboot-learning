package com.github.lybgeek.config.autoconfigure;

import com.github.lybgeek.config.binder.PropertyRebinder;
import com.github.lybgeek.config.binder.support.LoggerPropertyRebinder;
import com.github.lybgeek.config.helper.RefreshPropertyHelper;
import com.github.lybgeek.config.listener.PropertyRefreshChangeListener;
import com.github.lybgeek.config.listener.PropertyRefreshedChangeListener;
import com.github.lybgeek.config.property.RefreshConfigProperty;
import com.github.lybgeek.config.sync.PropertyRefreshedSync;
import com.github.lybgeek.config.sync.support.PropertyRefreshedSyncToFile;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@EnableConfigurationProperties(RefreshConfigProperty.class)
@ConditionalOnProperty(prefix = RefreshConfigProperty.PREFIX, name = "enabled", havingValue = "true",matchIfMissing = true)
public class RefreshPropertyAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public LoggerPropertyRebinder loggerPropertyRebinder(){
        return new LoggerPropertyRebinder();
    }

    @Bean
    @ConditionalOnMissingBean
    public RefreshPropertyHelper refreshPropertyHelper(InetUtils inetUtils, WebEndpointProperties webEndpointProperties){
        return new RefreshPropertyHelper(inetUtils,webEndpointProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PropertyRefreshChangeListener propertyRefreshChangeListener(ObjectProvider<List<PropertyRebinder>> objectProvider,RefreshConfigProperty refreshConfigProperty){
        return new PropertyRefreshChangeListener(objectProvider,refreshConfigProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    public PropertyRefreshedChangeListener propertyRefreshedChangeListener(ObjectProvider<List<PropertyRefreshedSync>> objectProvider, RefreshConfigProperty refreshConfigProperty){
        return new PropertyRefreshedChangeListener(objectProvider,refreshConfigProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    public PropertyRefreshedSyncToFile propertyRefreshedSyncToFile(RefreshConfigProperty refreshConfigProperty){
        return new PropertyRefreshedSyncToFile(refreshConfigProperty);
    }


}
