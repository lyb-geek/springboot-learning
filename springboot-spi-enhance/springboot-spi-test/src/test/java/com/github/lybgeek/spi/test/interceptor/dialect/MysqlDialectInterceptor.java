package com.github.lybgeek.spi.test.interceptor.dialect;


import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.dialect.mysql.MysqlDialect;
import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.Plugin;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;

@Intercepts(@Signature(type = SqlDialect.class,method = "dialect",args = {}))
public class MysqlDialectInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MysqlDialectInterceptor");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }


    @Override
    public Object plugin(Object target) {
        if(target.toString().startsWith(MysqlDialect.class.getName())){
            return Plugin.wrap(target,this);
        }
        return target;
    }
}
