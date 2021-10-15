package com.github.lybgeek.dialect.oracle;


import com.github.lybgeek.dialect.SpringSqlDialect;
import com.github.lybgeek.dialect.oracle.service.OracleDialectService;
import com.github.lybgeek.spi.anotatation.Activate;
import org.springframework.beans.factory.annotation.Autowired;

@Activate
public class SpringOracleDialect implements SpringSqlDialect {

    @Autowired
    private OracleDialectService oracleDialectService;

    @Override
    public String dialect() {
        return oracleDialectService.dialect();
    }


}
