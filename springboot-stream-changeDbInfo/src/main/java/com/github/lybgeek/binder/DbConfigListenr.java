package com.github.lybgeek.binder;

import com.alibaba.fastjson.JSON;
import com.github.lybgeek.dto.DbConfigInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DbConfigListenr{



  @StreamListener(value= DbConfigStreamBinder.OPERATE_LOG_TOPIC)
  public void receiveFromStream(String dbConfigJson){

    try {
      DbConfigInfoDTO dbConfigInfoDTO = JSON.parseObject(dbConfigJson,DbConfigInfoDTO.class);
      log.info("接收到信息：{}",dbConfigInfoDTO);
    } catch (Exception e) {
     log.error(e.getMessage(),e);
    }
  }

}
