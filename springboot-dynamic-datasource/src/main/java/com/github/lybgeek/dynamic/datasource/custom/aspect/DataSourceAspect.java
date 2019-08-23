package com.github.lybgeek.dynamic.datasource.custom.aspect;


import com.github.lybgeek.dynamic.datasource.custom.DataSourceName;
import com.github.lybgeek.dynamic.datasource.custom.DynamicDataSource;
import com.github.lybgeek.dynamic.datasource.custom.annotation.DataSource;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 多数据源，切面处理类
 * 
 */
@Aspect
@Component
@Profile(value = "custom")
public class DataSourceAspect implements Ordered {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("@annotation(com.github.lybgeek.dynamic.datasource.custom.annotation.DataSource)")
	public void dataSourcePointCut() {

	}

	@Around("dataSourcePointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		DataSource ds = method.getAnnotation(DataSource.class);
		if (ds == null) {
			DynamicDataSource.setDataSource(DataSourceName.MASTER);
			logger.debug("set datasource is " + DataSourceName.SLAVE);
		} else {
			DynamicDataSource.setDataSource(ds.name());
			logger.debug("set datasource is " + ds.name());
		}

		try {
			return point.proceed();
		} finally {
			DynamicDataSource.clearDataSource();
			logger.debug("clean datasource");
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
