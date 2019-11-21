package com.github.lybgeek.downgrade.service.impl;

import com.github.lybgeek.downgrade.service.HelloService;
import org.springframework.stereotype.Service;

@Service("helloFallbackService")
public class HelloFallBackServiceImpl implements HelloService {

  @Override
  public String hello(String userName) {

    return "fallBack:"+userName;
  }
}
