package com.github.lybgeek.log;


import com.github.lybgeek.spi.SpiLoader;
import com.github.lybgeek.spi.factory.SpiFactoriesLoader;

public class LogMainTest {
    public static void main(String[] args) {
        LogService logService = SpiLoader.getExtensionLoader(LogService.class).getTarget("log4j2");
        logService.info("log4j2-hello");

        logService = SpiFactoriesLoader.loadFactories().getTarget("log4j",LogService.class);
        logService.info("log4j-hello");

    }
}
