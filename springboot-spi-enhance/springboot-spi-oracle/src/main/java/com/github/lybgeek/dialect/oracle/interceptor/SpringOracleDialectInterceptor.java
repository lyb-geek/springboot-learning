package com.github.lybgeek.dialect.oracle.interceptor;


import com.github.lybgeek.dialect.SpringSqlDialect;
import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.dialect.oracle.SpringOracleDialect;
import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.Plugin;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;

@Intercepts(@Signature(type = SpringSqlDialect.class,method = "dialect",args = {}))
public class SpringOracleDialectInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("SpringOracleDialectInterceptor...");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public Object plugin(Object target) {
        if(target.toString().startsWith(SpringOracleDialect.class.getName())){
            return Plugin.wrap(target,this);
        }
        return target;
    }
}
