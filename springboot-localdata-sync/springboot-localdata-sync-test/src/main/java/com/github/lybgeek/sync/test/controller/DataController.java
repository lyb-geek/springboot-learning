package com.github.lybgeek.sync.test.controller;


import com.github.lybgeek.sync.test.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("data")
@RequiredArgsConstructor
public class DataController {
    


    private final DataService dataService;

    @GetMapping("add/{data}")
    public String syncData(@PathVariable("data") String data){
        boolean isSuccess = dataService.add(data);
        return isSuccess ? "success" : "fail";
    }

    @GetMapping("list")
    public List<Object> listData(){
        return dataService.getDataList();
    }
}
