package com.github.lybgeek.influxdb.autoconfigure;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.autoconfigure.influx.InfluxDbProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @description: influxdb配置
 *
 **/
@ConfigurationProperties(prefix = "spring.influx")
@Data
@ToString(callSuper = true)
@Primary
@Component
public class CustomInfluxDbProperties extends InfluxDbProperties {

    /**
     *  datasource.
     */
    private String datasource;


}
