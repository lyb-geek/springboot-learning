package com.github.lybgeek.loadbalance.updater;


import com.netflix.loadbalancer.ServerListUpdater;

public class EmptyServerListUpdater implements ServerListUpdater {
    @Override
    public void start(UpdateAction updateAction) {
        System.out.println("啥都不干。。。");
    }

    @Override
    public void stop() {

    }

    @Override
    public String getLastUpdate() {
        return null;
    }

    @Override
    public long getDurationSinceLastUpdateMs() {
        return 0;
    }

    @Override
    public int getNumberMissedCycles() {
        return 0;
    }

    @Override
    public int getCoreThreads() {
        return 0;
    }
}
