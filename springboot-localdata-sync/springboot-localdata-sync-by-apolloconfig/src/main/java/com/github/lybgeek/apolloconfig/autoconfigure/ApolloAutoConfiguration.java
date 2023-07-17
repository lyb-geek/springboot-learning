package com.github.lybgeek.apolloconfig.autoconfigure;

import com.github.lybgeek.apolloconfig.listener.ApolloConfigKeyChangeListener;
import com.github.lybgeek.apolloconfig.property.ApolloOpenApiProperty;
import com.github.lybgeek.apolloconfig.service.ApolloConfigService;
import com.github.lybgeek.apolloconfig.service.impl.ApolloConfigServiceImpl;
import com.github.lybgeek.apolloconfig.trigger.ApolloConfigDataSyncTrigger;
import com.github.lybgeek.common.constant.DataSyncConstant;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(ApolloOpenApiProperty.class)
public class ApolloAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public ApolloConfigService apolloConfigService(ApolloOpenApiProperty property){
        return new ApolloConfigServiceImpl(property);
    }



    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DataSyncTriggerProperty.PREFIX,name = "plugin",havingValue = DataSyncConstant.TRIGGER_TYPE_APOLLO_CONFIG)
    public BaseDataSyncTrigger apolloConfigDataSyncTrigger(ApolloConfigService apolloConfigService,DataSyncTriggerProperty dataSyncTriggerProperty){
        return new ApolloConfigDataSyncTrigger(apolloConfigService,dataSyncTriggerProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(name = "apolloConfigDataSyncTrigger")
    public ApolloConfigKeyChangeListener apolloConfigKeyChangeListener(BaseDataSyncTrigger apolloConfigDataSyncTrigger){
        return new ApolloConfigKeyChangeListener(apolloConfigDataSyncTrigger);
    }



}