package com.github.lybgeek.orm.mybatis.interceptor;


import com.github.lybgeek.orm.mybatis.annotation.CreateDate;
import com.github.lybgeek.orm.mybatis.annotation.UpdateDate;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * 添加时间注解拦截器,通过拦截sql，自动给带注解的属性添加时间
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
        Object.class})})
public class DateTimeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation)
            throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // 获取 SQL
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // 获取参数
        Object parameter = invocation.getArgs()[1];

        // 获取私有成员变量
        Field[] declaredFields = parameter.getClass().getDeclaredFields();

//        Class parentClz = parameter.getClass().getSuperclass();
//
//        if(parentClz.newInstance() instanceof BaseEntity){
//            declaredFields = parentClz.getDeclaredFields();
//        }


        for (Field field : declaredFields) {
            if (field.getAnnotation(CreateDate.class) != null) {
                if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                    // insert语句插入createTime
                    field.setAccessible(true);
                    // 这里设置时间，当然时间格式可以自定。比如转成String类型
                    field.set(parameter, new Date());
                }
            } else if (field.getAnnotation(UpdateDate.class) != null) {

                if (SqlCommandType.INSERT.equals(sqlCommandType)
                        || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                    // insert 或update语句插入updateTime
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
