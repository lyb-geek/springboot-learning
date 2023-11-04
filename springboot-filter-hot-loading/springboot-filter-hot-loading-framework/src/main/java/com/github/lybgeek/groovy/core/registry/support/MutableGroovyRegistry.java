package com.github.lybgeek.groovy.core.registry.support;

import com.github.lybgeek.groovy.core.registry.GroovyRegistry;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public final class MutableGroovyRegistry<T> implements GroovyRegistry<T> {
    private final ConcurrentHashMap<String, T> objectMap = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public T remove(String key) {
        return objectMap.remove(requireNonNull(key, "key"));
    }

    @Override
    @Nullable
    public T get(String key) {
        return objectMap.get(requireNonNull(key, "key"));
    }

    @Override
    public void put(String key, T object) {
        objectMap.putIfAbsent(requireNonNull(key, "key"), requireNonNull(object, "object"));
    }

    @Override
    public int size() {
        return objectMap.size();
    }

    @Override
    public Collection<T> getAllObjects() {
        return Collections.unmodifiableList(new ArrayList<>(objectMap.values()));
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
