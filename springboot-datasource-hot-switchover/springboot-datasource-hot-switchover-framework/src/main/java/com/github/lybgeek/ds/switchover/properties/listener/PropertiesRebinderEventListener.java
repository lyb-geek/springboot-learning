package com.github.lybgeek.ds.switchover.properties.listener;


import com.github.lybgeek.ds.switchover.properties.context.BackupDataSourcePropertiesHolder;
import com.github.lybgeek.ds.switchover.properties.event.PropertiesRebinderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Slf4j
public class PropertiesRebinderEventListener implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Resource
    private BackupDataSourcePropertiesHolder backupDataSourcePropertiesHolder;



    @EventListener
    public void change(PropertiesRebinderEvent event){
 //      BackupDataSourceProperties backupDataSourceProperties = backupDataSourcePropertiesHolder.rebinder();
//        System.out.println(backupDataSourceProperties.getBackup().getUsername());
//        System.out.println(backupDataSourceProperties.getBackup().getUrl());
//        System.out.println(backupDataSourceProperties.getBackup().getPassword());
//        System.out.println(backupDataSourceProperties.getBackup().getDriverClassName());
//        System.out.println(backupDataSourceProperties.isForceswitch());


    }




    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
