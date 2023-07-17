package com.github.lybgeek.common.autoconfigure;


import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(DataSyncTriggerProperty.class)
public class DataSyncTriggerConfiguration {



}