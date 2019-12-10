package com.github.lybgeek.common;

import com.github.lybgeek.common.hook.enu.SignalType;
import com.github.lybgeek.common.hook.handler.ShutDownHookSignalHandler;
import com.github.lybgeek.common.hook.util.ShutDownHookUtil;
import com.github.lybgeek.common.util.ThreadPoolUtil;
import com.github.lybgeek.common.util.YmlUtil;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;

@Slf4j
public class Application {

  public static void run(Class<?> primarySource, String... args){
    String webType = YmlUtil.getValue("spring.main.web-application-type").toString();
    SpringApplication.run(primarySource,args);
    if(WebApplicationType.NONE.name().equalsIgnoreCase(webType)){
      gracefulShutdown(()->{
        boolean isClose = ThreadPoolUtil.INSTANCE.close();
        if(isClose){
          log.info("see you next time!");
        }
      },SignalType.USR2.getType());
     block();
    }
  }


  private static void join(){
    Semaphore semaphore = new Semaphore(0);
    try {
      semaphore.acquire();
    } catch (InterruptedException e) {
      log.error(e.getMessage(),e);
    }
  }

  private static void block(){

    try {
      System.in.read();
    } catch (IOException e) {
      log.error(e.getMessage(),e);
    }
  }

  /**
   * addShutdownHook方法和handle方法中如果再调用System.exit，会造成死锁，使进程无法正常退出
   * @param runnable
   */
  public static boolean gracefulShutdown(Runnable runnable,String signalName){
    ShutDownHookUtil.INSTANCE.release(runnable);
    gracefulShutdown(signalName);
    return true;
  }

  /**
   * addShutdownHook方法和handle方法中如果再调用System.exit，会造成死锁，使进程无法正常退出
   */
  public static boolean gracefulShutdown(String signalName){
    ShutDownHookSignalHandler shutDownHookSignalHandler = new ShutDownHookSignalHandler();
    shutDownHookSignalHandler.registerSignal(signalName);
    return true;
  }

}
