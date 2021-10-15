package com.github.lybgeek.dialect.mysql.interceptor;


import com.github.lybgeek.dialect.SpringSqlDialect;
import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.dialect.mysql.SpringMysqlDialect;
import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.Plugin;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;

@Intercepts(@Signature(type = SpringSqlDialect.class,method = "dialect",args = {}))
public class SpringMysqlDialectInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("SpringMysqlDialectInterceptor...");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }


    @Override
    public Object plugin(Object target) {
        if(target.toString().startsWith(SpringMysqlDialect.class.getName())){
            return Plugin.wrap(target,this);
        }
        return target;
    }
}
