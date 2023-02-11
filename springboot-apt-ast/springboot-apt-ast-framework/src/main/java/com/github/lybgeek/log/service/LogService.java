package com.github.lybgeek.log.service;


import com.github.lybgeek.log.model.LogDTO;

@FunctionalInterface
public interface LogService {

    void save(LogDTO logDTO);

    default int order(){
        return 0;
    }
}
