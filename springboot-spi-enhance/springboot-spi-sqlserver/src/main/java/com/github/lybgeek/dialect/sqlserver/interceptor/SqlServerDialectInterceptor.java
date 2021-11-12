package com.github.lybgeek.dialect.sqlserver.interceptor;


import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.dialect.sqlserver.SqlServerDialect;
import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.Plugin;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;

@Intercepts(@Signature(type = SqlDialect.class,method = "dialect",args = {}))
public class SqlServerDialectInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("SqlServerDialectInterceptor...");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }


    @Override
    public Object plugin(Object target) {
        if(target.toString().startsWith(SqlServerDialect.class.getName())){
            return Plugin.wrap(target,this);
        }
        return target;
    }
}
