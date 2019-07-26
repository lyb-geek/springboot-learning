package com.github.lybgeek.event;

import com.github.lybgeek.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogEvent {

  @Autowired
  private OperateLogService operateLogService;

  @EventListener
  @Async
  public void saveLog(String content){
    log.info("content:{}",content);
    operateLogService.saveLog(content);

  }

}
