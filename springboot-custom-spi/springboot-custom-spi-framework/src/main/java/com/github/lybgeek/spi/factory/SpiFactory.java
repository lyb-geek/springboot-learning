package com.github.lybgeek.spi.factory;

import java.util.List;
import java.util.Map;


public interface SpiFactory {

    /**
     * Gets Extension.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param clazz the clazz
     * @return the extension
     */
    <T> T getTarget(String key, Class<T> clazz);

    <T> List<T> getTargets(Class<T> clazz);

   <T> Map<String, Class<?>> getTargetClassesMap(Class<T> clazz);





}
