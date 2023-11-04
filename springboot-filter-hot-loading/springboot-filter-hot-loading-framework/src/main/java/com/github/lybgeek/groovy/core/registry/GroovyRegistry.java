package com.github.lybgeek.groovy.core.registry;


import org.springframework.lang.Nullable;

import java.util.Collection;

public interface GroovyRegistry<T> {
    @Nullable
    T get(String key);

    int size();

    Collection<T> getAllObjects();

    /**
     * Indicates if this registry can be modified.  Implementations should not change the return;
     * they return the same value each time.
     */
    boolean isMutable();

    /**
     * Removes the filter from the registry, and returns it.   Returns {@code null} no such filter
     * was found.  Callers should check {@link #isMutable()} before calling this method.
     *
     * @throws IllegalStateException if this registry is not mutable.
     */
    @Nullable
    T remove(String key);

    /**
     * Stores the filter into the registry.  If an existing filter was present with the same key,
     * it is removed.  Callers should check {@link #isMutable()} before calling this method.
     *
     * @throws IllegalStateException if this registry is not mutable.
     */
    void put(String key, T object);
}
