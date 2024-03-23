package com.github.lybgeek.config.binder;


import com.github.lybgeek.config.model.RefreshProperty;

@FunctionalInterface
public interface PropertyRebinder {

    void binder(RefreshProperty refreshProperty);
}
