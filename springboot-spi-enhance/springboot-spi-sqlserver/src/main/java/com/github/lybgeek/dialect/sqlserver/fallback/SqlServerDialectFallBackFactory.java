package com.github.lybgeek.dialect.sqlserver.fallback;


import com.github.lybgeek.circuitbreaker.fallback.FallbackFactory;
import com.github.lybgeek.dialect.SqlDialect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqlServerDialectFallBackFactory implements FallbackFactory<SqlDialect> {

    @Override
    public SqlDialect create(Throwable ex) {
        return () -> {
            log.error("{}",ex);
            return "SqlServerDialect FallBackFactory";
        };
    }
}
