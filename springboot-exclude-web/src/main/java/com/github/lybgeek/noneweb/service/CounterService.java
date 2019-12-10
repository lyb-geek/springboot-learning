package com.github.lybgeek.noneweb.service;

import com.github.lybgeek.common.util.ThreadPoolUtil;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
@Slf4j
public class CounterService {



  public void counter(final int maxCount,final long time){
    log.info("计数器");
    ThreadPoolUtil.INSTANCE.getThreadPool().submit(()->{
       StopWatch stopWatch = new StopWatch("counter");
       stopWatch.start();
       try {
         for (int i = 0; i < maxCount; i++) {
           log.info("count:{}",i+1);
           Thread.sleep(time);
         }
       } catch (InterruptedException e) {
         log.error(e.getMessage(),e);
       }
       stopWatch.stop();
       log.info("{}",stopWatch.prettyPrint());

     });
  }

  //@PostConstruct
  public void init(){
    this.counter(100,600);
  }




}
