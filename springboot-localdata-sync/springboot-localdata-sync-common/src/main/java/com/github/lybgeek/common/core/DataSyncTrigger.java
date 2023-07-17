package com.github.lybgeek.common.core;


@FunctionalInterface
public interface DataSyncTrigger {

    void broadcast(Object data);
}
