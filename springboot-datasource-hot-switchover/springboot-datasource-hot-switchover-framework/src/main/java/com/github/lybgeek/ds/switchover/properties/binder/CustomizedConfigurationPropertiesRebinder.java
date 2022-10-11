package com.github.lybgeek.ds.switchover.properties.binder;

import com.github.lybgeek.ds.switchover.properties.event.PropertiesRebinderEvent;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.ApplicationContext;


public class CustomizedConfigurationPropertiesRebinder extends ConfigurationPropertiesRebinder {

    private ConfigurationPropertiesBeans beans;
    public CustomizedConfigurationPropertiesRebinder(ConfigurationPropertiesBeans beans) {
        super(beans);
        this.beans = beans;
    }

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        if (this.applicationContext.equals(event.getSource())
                // Backwards compatible
                || event.getKeys().equals(event.getSource())) {
            rebind();
            applicationContext.publishEvent(new PropertiesRebinderEvent(event.getKeys()));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

}
