package com.github.lybgeek.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum  ThreadPoolUtil {


  INSTANCE;

  private static final ExecutorService executorService = Executors.newSingleThreadExecutor();



  public ExecutorService getThreadPool(){
    return executorService;
  }


  public void submitTask(Runnable runnable){
    getThreadPool().submit(runnable);
  }


  public boolean close(){
     getThreadPool().shutdown();
     boolean isClose;
     while(!getThreadPool().isTerminated()){
       try {
         Thread.sleep(500);
       } catch (InterruptedException e) {
         log.error(e.getMessage(),e);
       }
     }
     isClose = true;
     return isClose;
  }

}
