package com.github.lybgeek.listenr;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface LogStreamBinder {

  String DB_CONFIG_TOPIC = "dbConfigTopic";

  String OPERATE_LOG_TOPIC = "operateLogTopic";


  @Input(DB_CONFIG_TOPIC)
  SubscribableChannel dbConfig();

  @Output(OPERATE_LOG_TOPIC)
  MessageChannel operateLog();

}
