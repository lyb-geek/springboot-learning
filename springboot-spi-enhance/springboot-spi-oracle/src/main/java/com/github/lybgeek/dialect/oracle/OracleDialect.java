package com.github.lybgeek.dialect.oracle;


import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.spi.anotatation.Activate;

@Activate
public class OracleDialect implements SqlDialect {
    @Override
    public String dialect() {
        return "oracle";
    }


}
