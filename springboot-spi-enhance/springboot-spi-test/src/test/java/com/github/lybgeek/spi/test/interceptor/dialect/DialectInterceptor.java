package com.github.lybgeek.spi.test.interceptor.dialect;

import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;

@Intercepts(@Signature(type = SqlDialect.class,method = "dialect",args = {}))
public class DialectInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("DialectInterceptor");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
