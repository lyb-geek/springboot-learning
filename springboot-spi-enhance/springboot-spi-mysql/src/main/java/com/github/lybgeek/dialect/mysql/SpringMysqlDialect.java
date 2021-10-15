package com.github.lybgeek.dialect.mysql;


import com.github.lybgeek.dialect.SpringSqlDialect;
import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.dialect.mysql.service.MysqlDialectService;
import com.github.lybgeek.spi.anotatation.Activate;
import org.springframework.beans.factory.annotation.Autowired;

@Activate("hello-mysql")
public class SpringMysqlDialect implements SpringSqlDialect {

    @Autowired
    private MysqlDialectService mysqlDialectService;

    @Override
    public String dialect() {
        return mysqlDialectService.dialect();
    }


}
