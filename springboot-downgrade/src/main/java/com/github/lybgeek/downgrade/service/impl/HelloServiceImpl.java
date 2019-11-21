package com.github.lybgeek.downgrade.service.impl;

import com.github.lybgeek.downgrade.annotation.ResouceDowngrade;
import com.github.lybgeek.downgrade.service.HelloService;
import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloServiceImpl implements HelloService {

  @Override
  @ResouceDowngrade(fallbackClass = HelloFallBackServiceImpl.class,resouceId = "hello")
  public String hello(String userName) {

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
    }
    return "hello:" + userName;
  }
}
