package com.github.lybgeek.ds.switchover.core.manager.support;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.lybgeek.ds.switchover.core.ds.DynamicDataSource;
import com.github.lybgeek.ds.switchover.core.manager.AbstractDataSourceManger;
import com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.lybgeek.ds.switchover.core.ds.DynamicDataSource.DATASOURCE_KEY;


@Slf4j
public class DruidDataSourceManger extends AbstractDataSourceManger {

    public DruidDataSourceManger(BackupDataSourceProperties backupDataSourceProperties, DataSourceProperties dataSourceProperties) {
        super(backupDataSourceProperties, dataSourceProperties);
    }

    @Override
    public DataSource createDataSource(boolean isBackup) {
        DruidDataSource dataSource = new DruidDataSource();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r,"create-db-pool"));
        if(isBackup){
            dataSource.setUrl(backupDataSourceProperties.getBackup().getUrl());
            dataSource.setUsername(backupDataSourceProperties.getBackup().getUsername());
            dataSource.setPassword(backupDataSourceProperties.getBackup().getPassword());
            dataSource.setCreateScheduler(scheduledExecutorService);
        }else{
            dataSource.setUrl(dataSourceProperties.getUrl());
            dataSource.setUsername(dataSourceProperties.getUsername());
            dataSource.setPassword(dataSourceProperties.getPassword());
            dataSource.setCreateScheduler(scheduledExecutorService);
        }
        return dataSource;
    }

    @SneakyThrows
    @Override
    public void switchBackupDataSource() {
        if(backupDataSourceProperties.isForceswitch()){
            if(backupDataSourceProperties.isForceswitch()){
                log.info("Start to switch backup dataSource to : 【{}】",backupDataSourceProperties.getBackup().getUrl());
                DataSource dataSource = this.createDataSource(true);
                DynamicDataSource source = applicationContext.getBean(DynamicDataSource.class);
                DataSource originalDetermineTargetDataSource = source.getOriginalDetermineTargetDataSource();
                if(originalDetermineTargetDataSource instanceof DruidDataSource){
                    DruidDataSource druidDataSource = (DruidDataSource)originalDetermineTargetDataSource;
                    ScheduledExecutorService createScheduler = druidDataSource.getCreateScheduler();
                    createScheduler.shutdown();
                    if(!createScheduler.awaitTermination(backupDataSourceProperties.getAwaittermination(), TimeUnit.SECONDS)){
                        log.warn("Druid dataSource 【{}】 create connection thread force to closed",druidDataSource.getUrl());
                        createScheduler.shutdownNow();
                    }
                }
                //当检测到数据库地址改变时，重新设置数据源
                source.setTargetDataSources(Collections.singletonMap(DATASOURCE_KEY, dataSource));
                //调用该方法刷新resolvedDataSources，下次获取数据源时将获取到新设置的数据源
                source.afterPropertiesSet();

                log.info("Switch backup dataSource to : 【{}】 finished",backupDataSourceProperties.getBackup().getUrl());
            }
        }
    }
}
