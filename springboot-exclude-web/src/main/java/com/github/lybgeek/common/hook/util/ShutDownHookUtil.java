package com.github.lybgeek.common.hook.util;

/**
 * 进程被kill的时候main函数就已经结束了，仅会运行shutdownHook中run()方法的代码.
 * SIGTERM,SIGINT,SIGHUP三种信号都会触发该方法（分别对应kill -1/kill -2/kill -15，Ctrl+C也会触发SIGINT信号）。
 */
public enum  ShutDownHookUtil {

  INSTANCE;

  public void release(Runnable runnable){
    Runtime.getRuntime().addShutdownHook(new Thread(runnable));
  }

}
