package com.github.lybgeek.controller;

import com.github.lybgeek.model.OperateLog;
import com.github.lybgeek.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/log")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;


    @GetMapping(value="/list")
    public List<OperateLog> listOperateLogs(){
        return operateLogService.listOperateLogs();
    }
}
