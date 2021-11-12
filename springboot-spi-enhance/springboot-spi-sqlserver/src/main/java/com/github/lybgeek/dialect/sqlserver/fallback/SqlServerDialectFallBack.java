package com.github.lybgeek.dialect.sqlserver.fallback;


import com.github.lybgeek.dialect.SqlDialect;
import org.springframework.stereotype.Component;

@Component
public class SqlServerDialectFallBack implements SqlDialect {
    @Override
    public String dialect() {
        return "sqlServerDialect fallBack";
    }
}
