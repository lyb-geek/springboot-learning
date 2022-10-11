package com.github.lybgeek.ds.switchover.properties.refresh;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

import com.github.lybgeek.ds.switchover.core.ds.DynamicDataSource;
import com.github.lybgeek.ds.switchover.core.manager.AbstractDataSourceManger;
import com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties;
import com.github.lybgeek.ds.switchover.properties.context.BackupDataSourcePropertiesHolder;
import com.github.lybgeek.ds.switchover.properties.listener.PropertiesRebinderEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.Set;

import static com.github.lybgeek.ds.switchover.core.ds.DynamicDataSource.DATASOURCE_KEY;
import static com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties.PREFIX;


@Component
@Slf4j
public class BackupDataSourcePropertiesRefresh implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private BackupDataSourcePropertiesHolder backupDataSourcePropertiesHolder;

    @Resource
    private AbstractDataSourceManger abstractDataSourceManger;


    @ApolloConfigChangeListener(interestedKeyPrefixes = PREFIX)
    public void onChange(ConfigChangeEvent changeEvent) {
        refresh(changeEvent.changedKeys());
    }

    /**
     *
     * @param changedKeys
     */
    private synchronized void refresh(Set<String> changedKeys) {
        /**
         * rebind configuration beans, e.g. DataSourceProperties
         * @see org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder#onApplicationEvent
         */
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));

        /**
         * BackupDataSourceProperties rebind ,you can also do it in PropertiesRebinderEventListener
         * @see PropertiesRebinderEventListener
         */
        backupDataSourcePropertiesHolder.rebinder();

        abstractDataSourceManger.switchBackupDataSource();


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
