package com.github.lybgeek.dynamic.datasource.custom.annotation;

import com.github.lybgeek.dynamic.datasource.custom.DataSourceName;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多数据源注解
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	DataSourceName name() default DataSourceName.MASTER;
}
