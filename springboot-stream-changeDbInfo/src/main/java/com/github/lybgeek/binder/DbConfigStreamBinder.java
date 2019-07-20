package com.github.lybgeek.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DbConfigStreamBinder {

  String DB_CONFIG_TOPIC = "dbConfigTopic";

  String OPERATE_LOG_TOPIC = "operateLogTopic";


  @Input(OPERATE_LOG_TOPIC)
  SubscribableChannel operateLog();

  @Output(DB_CONFIG_TOPIC)
  MessageChannel dbConfig();

}
