package com.github.lybgeek.downgrade.controller;

import com.github.lybgeek.downgrade.service.HelloService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

  @Resource(name="helloService")
  private HelloService helloService;

  @GetMapping(value="/hello/{userName}")
  public String hello(@PathVariable("userName") String userName){
    return helloService.hello(userName);
  }

}
