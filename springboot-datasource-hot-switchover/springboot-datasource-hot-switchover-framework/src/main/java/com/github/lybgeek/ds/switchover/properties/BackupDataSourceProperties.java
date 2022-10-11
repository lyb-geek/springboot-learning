package com.github.lybgeek.ds.switchover.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.github.lybgeek.ds.switchover.properties.BackupDataSourceProperties.PREFIX;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class BackupDataSourceProperties {

    public static final String PREFIX = "lybgeek.datasource";

    /**
     * Force switch to backup db sever,default false
     */
    private boolean forceswitch;

    /**
     * Create connection thread max wait time;default 15 second;unit second
     */
    private long awaittermination = 15;

    @NestedConfigurationProperty
    private DataSourceProperties backup;
}
