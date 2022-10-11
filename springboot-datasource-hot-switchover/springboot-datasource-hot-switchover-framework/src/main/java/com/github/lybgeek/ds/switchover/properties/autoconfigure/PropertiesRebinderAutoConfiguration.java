package com.github.lybgeek.ds.switchover.properties.autoconfigure;

import com.github.lybgeek.ds.switchover.properties.binder.CustomizedConfigurationPropertiesRebinder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AutoConfigureBefore(ConfigurationPropertiesRebinderAutoConfiguration.class)
public class PropertiesRebinderAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
    public ConfigurationPropertiesRebinder configurationPropertiesRebinder(
            ConfigurationPropertiesBeans beans) {
        CustomizedConfigurationPropertiesRebinder rebinder = new CustomizedConfigurationPropertiesRebinder(
                beans);
        return rebinder;
    }
}
