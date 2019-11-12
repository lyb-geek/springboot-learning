package com.github.lybgeek.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrometheusController {

  @RequestMapping(value="/")
  public String index(){
    return "Hello Prometheus";
  }

}
