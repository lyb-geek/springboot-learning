package com.github.lybgeek.db.check.processor;

import com.alibaba.druid.pool.ExceptionSorter;
import com.alibaba.druid.pool.ValidConnectionChecker;
import com.alibaba.druid.util.JdbcUtils;
import com.github.lybgeek.db.check.MySqlExceptionSorterExt;
import com.github.lybgeek.db.check.MysqlConnectionCheck;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class DruidDataSourceBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor, ApplicationContextAware {

    private DataSourceProperties dataSourceProperties;

    private ApplicationContext applicationContext;

    public DruidDataSourceBeanPostProcessor(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clz = Class.forName("com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper");
        if (clz.isAssignableFrom(bean.getClass())) {
            String driverClassName = dataSourceProperties.getDriverClassName();
            setValidConnectionChecker(bean, clz, driverClassName);
            setExceptionSort(bean, clz, driverClassName);

        }
        return bean;
    }

    private void setValidConnectionChecker(Object bean, Class clz, String driverClassName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object validConnectionChecker = clz.getMethod("getValidConnectionChecker").invoke(bean);
        Object validConnectionCheckerClassName = clz.getMethod("getValidConnectionCheckerClassName").invoke(bean);
        if(ObjectUtils.isEmpty(validConnectionChecker) &&
                ObjectUtils.isEmpty(validConnectionCheckerClassName)){
            if (JdbcUtils.isMySqlDriver(driverClassName)) {
                Method method = clz.getMethod("setValidConnectionChecker",ValidConnectionChecker.class);
                method.invoke(bean,new MysqlConnectionCheck(applicationContext));
            }
        }
    }

    private void setExceptionSort(Object bean, Class clz, String driverClassName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object exceptionSorter = clz.getMethod("getExceptionSorter").invoke(bean);
        Object exceptionSorterClassName = clz.getMethod("getExceptionSorterClassName").invoke(bean);

        if(ObjectUtils.isEmpty(exceptionSorter) &&
                ObjectUtils.isEmpty(exceptionSorterClassName)){
            if (JdbcUtils.isMySqlDriver(driverClassName)) {
                Method method = clz.getMethod("setExceptionSorter", ExceptionSorter.class);
                MySqlExceptionSorterExt mySqlExceptionSorterExt = new MySqlExceptionSorterExt();
                mySqlExceptionSorterExt.setApplicationContext(applicationContext);
                method.invoke(bean,mySqlExceptionSorterExt);
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
