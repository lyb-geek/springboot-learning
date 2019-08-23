package com.github.lybgeek.dynamic.datasource.custom;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * 配置多数据源
 * 
 */
@Configuration
//##在profile为custom时，才生效
@Profile(value = "custom")
public class DynamicDataSourceConfig {
	protected Logger logger = LoggerFactory.getLogger(getClass());


	@Bean
	@ConfigurationProperties("spring.datasource.druid.master")
	public DataSource masterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.druid.slave")
	public DataSource slaveDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceName.MASTER,  masterDataSource);
		targetDataSources.put(DataSourceName.SLAVE, slaveDataSource);
		return new DynamicDataSource(masterDataSource, targetDataSources);
	}


}
