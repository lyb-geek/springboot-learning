package com.github.lybgeek.dynamic.datasource.custom;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private static final ThreadLocal<DataSourceName> contextHolder = new ThreadLocal<>();

	public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return getDataSource();
	}

	public static void setDataSource(DataSourceName dataSource) {
		contextHolder.set(dataSource);
	}

	public static DataSourceName getDataSource() {
		return contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}

}
