package com.github.lybgeek.dialect.sqlserver;


import com.github.lybgeek.circuitbreaker.annotation.CircuitBreakerActivate;
import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.dialect.sqlserver.fallback.SqlServerDialectFallBackFactory;

@CircuitBreakerActivate(spiKey = "sqlserver",fallbackFactory = SqlServerDialectFallBackFactory.class)
public class SqlServerDialect implements SqlDialect {
    @Override
    public String dialect() {
        return "sqlserver";
    }


}
