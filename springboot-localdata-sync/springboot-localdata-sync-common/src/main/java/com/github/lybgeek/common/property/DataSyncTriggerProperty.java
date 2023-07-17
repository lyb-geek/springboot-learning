package com.github.lybgeek.common.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = DataSyncTriggerProperty.PREFIX)
public class DataSyncTriggerProperty {

    public static final String PREFIX = "lybgeek.datasync";

    private String plugin;

    private boolean triggerCallBackAsync;
}
