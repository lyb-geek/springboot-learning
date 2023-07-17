package com.github.lybgeek.common.core;

@FunctionalInterface
public interface DataSyncTriggerCallBack {

    void execute(Object data);
}
