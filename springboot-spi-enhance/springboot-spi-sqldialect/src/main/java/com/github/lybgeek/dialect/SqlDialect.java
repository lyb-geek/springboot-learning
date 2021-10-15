package com.github.lybgeek.dialect;


import com.github.lybgeek.spi.anotatation.SPI;

@SPI("mysql")
public interface SqlDialect {

    String dialect();

}
