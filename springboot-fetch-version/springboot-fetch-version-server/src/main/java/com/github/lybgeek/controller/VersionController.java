package com.github.lybgeek.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("version")
public class VersionController {

    @PostMapping("report")
    public String report(@RequestBody String versionNosJson){
        System.out.println(">>>>>>>>>>>>>>>:" + versionNosJson);
        Map map = JSON.parseObject(versionNosJson, Map.class);
        map.forEach((k,v)-> System.out.println(k + ":" + v));

        return "success";

    }
}
