package com.github.lybgeek.influxdb.autoconfigure;

import com.github.lybgeek.influxdb.helper.InfluxdbHelpler;
import org.influxdb.InfluxDB;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: influxdb自动装配
 *
 **/
@Configuration
@ConditionalOnProperty("spring.influx.datasource")
@AutoConfigureAfter(InfluxDbAutoConfiguration.class)
public class CustomInfluxDbAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean
    InfluxdbHelpler influxdbHelpler(InfluxDB influxDB, CustomInfluxDbProperties influxDbProperties){
        return new InfluxdbHelpler(influxDB,influxDbProperties);
    }
}
