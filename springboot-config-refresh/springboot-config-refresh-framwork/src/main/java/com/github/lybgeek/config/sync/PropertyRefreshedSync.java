package com.github.lybgeek.config.sync;


public interface PropertyRefreshedSync {

    void execute(String name,Object value);
}
