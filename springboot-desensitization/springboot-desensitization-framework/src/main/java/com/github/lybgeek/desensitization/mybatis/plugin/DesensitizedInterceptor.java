package com.github.lybgeek.desensitization.mybatis.plugin;


import com.github.lybgeek.desensitization.util.EntityUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.List;
import java.util.Properties;

@Intercepts(@Signature(type = ResultSetHandler.class,method = "handleResultSets",args = Statement.class))
public class DesensitizedInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> list = (List<Object>) invocation.proceed();
        list.forEach(EntityUtils::desensitized);

        return list;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
