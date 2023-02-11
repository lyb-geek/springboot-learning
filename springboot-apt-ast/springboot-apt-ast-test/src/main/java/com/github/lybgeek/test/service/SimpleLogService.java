package com.github.lybgeek.test.service;


import com.github.lybgeek.log.model.LogDTO;
import com.github.lybgeek.log.service.LogService;
import com.google.auto.service.AutoService;


@AutoService(LogService.class)
public class SimpleLogService implements LogService {
    @Override
    public void save(LogDTO logDTO) {
        System.out.println(logDTO);
    }
}
