package com.github.lybgeek.common.hook.handler;

import com.github.lybgeek.common.hook.enu.SignalType;
import com.github.lybgeek.common.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * handle函数会在进程被kill时收到USR1信号，对main函数的运行不会有任何影响,
 *
 *
 */
@Slf4j
public class ShutDownHookSignalHandler implements SignalHandler {

  @Override
  public void handle(Signal signal) {

    SignalType signalType = SignalType.getSignal(signal.getName());

    switch (signalType){
      //kill -12
      case USR2:
        callbackByUSR1();
        break;
      //kill -15
      case TERM:
        break;
    }

  }


  private void callbackByUSR1(){
    boolean isClose = ThreadPoolUtil.INSTANCE.close();
    log.info("thread pool isClose:{}",isClose);
  }

  public void registerSignal(String signalName) {
    Signal signal = new Signal(signalName);
    Signal.handle(signal, this);
  }

}
