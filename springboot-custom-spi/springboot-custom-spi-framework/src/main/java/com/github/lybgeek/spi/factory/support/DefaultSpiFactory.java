package com.github.lybgeek.spi.factory.support;


import com.github.lybgeek.spi.SpiLoader;
import com.github.lybgeek.spi.factory.SpiFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DefaultSpiFactory implements SpiFactory {
    @Override
    public <T> T getTarget(String key, Class<T> clazz) {
        return SpiLoader.getExtensionLoader(Objects.requireNonNull(clazz)).getTarget(Objects.requireNonNull(key));

    }

    @Override
    public <T> List<T> getTargets(Class<T> clazz) {

        return SpiLoader.getExtensionLoader(Objects.requireNonNull(clazz)).getTargets();
    }

    @Override
    public <T> Map<String, Class<?>> getTargetClassesMap(Class<T> clazz) {

        return SpiLoader.getExtensionLoader(Objects.requireNonNull(clazz)).getTargetClassesMap();
    }
}
