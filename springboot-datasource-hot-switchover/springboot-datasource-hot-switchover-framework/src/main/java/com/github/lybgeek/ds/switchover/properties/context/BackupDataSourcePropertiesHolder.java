package com.github.lybgeek.ds.switchover.properties.context;


import com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties;
import com.github.lybgeek.ds.switchover.properties.binder.CustomizedConfigurationPropertiesBinder;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties.PREFIX;

@Component
public class BackupDataSourcePropertiesHolder implements ApplicationContextAware {

    @Resource
    private CustomizedConfigurationPropertiesBinder binder;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public BackupDataSourceProperties rebinder(){
        BackupDataSourceProperties backupDataSourceProperties = applicationContext.getBean(BackupDataSourceProperties.class);
        Bindable<BackupDataSourceProperties> bindable = Bindable.ofInstance(backupDataSourceProperties);
        binder.bind(PREFIX,bindable);
        return backupDataSourceProperties;
    }
}
