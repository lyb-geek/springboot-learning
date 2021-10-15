package com.github.lybgeek.dialect;

import com.github.lybgeek.spi.anotatation.SPI;

@SPI("springMysql")
public interface SpringSqlDialect {

    String dialect();
}
