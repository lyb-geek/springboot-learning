package com.github.lybgeek.docker.controller;

import com.github.lybgeek.docker.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

  @Autowired
  private RedisService redisService;

  @Value("${env}")
  private String env;

  @GetMapping("/{key}/{value}")
  public String setValue(@PathVariable("key")String key,@PathVariable("value")String value){

    return redisService.setValue(key,value) ? env + " setValue success" : "setValue fail";

  }

  @GetMapping("/{key}")
  public String getValue(@PathVariable("key")String key){
    return "use->"+env+" environment : hello ->" + redisService.getValue(key);
  }

}
