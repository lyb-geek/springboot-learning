package com.github.lybgeek.spi.factory;

import cn.hutool.core.util.ArrayUtil;
import com.github.lybgeek.spi.SpiLoader;
import com.github.lybgeek.spi.factory.support.DefaultSpiFactory;


public final class SpiFactoriesLoader {

    private SpiFactoriesLoader() {
    }

    public static SpiFactory loadFactories(String ... spiFactoryType) {
        if(ArrayUtil.isEmpty(spiFactoryType)){
            return SpiLoader.getExtensionLoader(SpiFactory.class).getTarget(DefaultSpiFactory.class.getSimpleName());
        }
        return SpiLoader.getExtensionLoader(SpiFactory.class).getTarget(spiFactoryType[0]);

    }
}
