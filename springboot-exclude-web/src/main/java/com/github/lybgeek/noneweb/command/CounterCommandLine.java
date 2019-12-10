package com.github.lybgeek.noneweb.command;

import com.github.lybgeek.noneweb.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CounterCommandLine implements CommandLineRunner {


  @Autowired
  private CounterService counterService;

  @Override
  public void run(String... args) throws Exception {
    counterService.counter(100,600);
  }
}
