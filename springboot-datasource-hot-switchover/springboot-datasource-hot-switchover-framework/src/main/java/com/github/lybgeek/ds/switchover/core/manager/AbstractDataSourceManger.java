package com.github.lybgeek.ds.switchover.core.manager;

import com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.sql.DataSource;


public abstract class AbstractDataSourceManger implements ApplicationContextAware {

    protected BackupDataSourceProperties backupDataSourceProperties;

    protected DataSourceProperties dataSourceProperties;

    protected ApplicationContext applicationContext;

    public AbstractDataSourceManger(BackupDataSourceProperties backupDataSourceProperties, DataSourceProperties dataSourceProperties) {
        this.backupDataSourceProperties = backupDataSourceProperties;
        this.dataSourceProperties = dataSourceProperties;
    }

    public abstract DataSource createDataSource(boolean isBackup);

    public abstract void switchBackupDataSource();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
