package com.github.lybgeek.dialect.mysql;


import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.spi.anotatation.Activate;

@Activate
public class MysqlDialect implements SqlDialect {
    @Override
    public String dialect() {
        return "mysql";
    }


}
