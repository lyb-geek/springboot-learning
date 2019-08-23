package com.github.lybgeek.dynamic.datasource.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 增加多数据源，在此配置
 *
 * 
 */
@Getter
@AllArgsConstructor
public enum  DataSourceName {
	MASTER("master","主机"),SLAVE("slave","备机");

	private String value;

	private String desc;

}
