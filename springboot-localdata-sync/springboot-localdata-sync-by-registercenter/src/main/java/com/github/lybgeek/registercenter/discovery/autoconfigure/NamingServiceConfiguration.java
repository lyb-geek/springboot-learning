package com.github.lybgeek.registercenter.discovery.autoconfigure;

import com.github.lybgeek.common.constant.DataSyncConstant;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import com.github.lybgeek.registercenter.discovery.NamingService;
import com.github.lybgeek.registercenter.discovery.controller.NotifyChangeController;
import com.github.lybgeek.registercenter.discovery.trigger.RegisterCenterDataSyncTrigger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NamingServiceConfiguration {



    @Bean
    @ConditionalOnMissingBean
    public NamingService namingService(DiscoveryClient discoveryClient){
        return new NamingService(discoveryClient);
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DataSyncTriggerProperty.PREFIX,name = "plugin",havingValue = DataSyncConstant.TRIGGER_TYPE_REGISTER_CENTER)
    public BaseDataSyncTrigger registerCenterDataSyncTrigger(NamingService namingService,DataSyncTriggerProperty dataSyncTriggerProperty){
        return new RegisterCenterDataSyncTrigger(namingService,dataSyncTriggerProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(name = "registerCenterDataSyncTrigger")
    public NotifyChangeController notifyChangeController(BaseDataSyncTrigger registerCenterDataSyncTrigger){
        return new NotifyChangeController(registerCenterDataSyncTrigger);
    }



}