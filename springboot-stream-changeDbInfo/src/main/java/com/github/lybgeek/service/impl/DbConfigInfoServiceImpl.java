package com.github.lybgeek.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.lybgeek.binder.DbConfigStreamBinder;
import com.github.lybgeek.dto.DbConfigInfoDTO;
import com.github.lybgeek.service.DbConfigInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DbConfigInfoServiceImpl implements DbConfigInfoService {

  @Autowired
  private DbConfigStreamBinder dbConfigBinder;

  @Override
  public String changeDbConfig(DbConfigInfoDTO dbConfigInfoDTO) {
    String json = JSON.toJSONString(dbConfigInfoDTO);
    boolean sendOk = dbConfigBinder.dbConfig().send(MessageBuilder.withPayload(json).build());
    if(sendOk){
      return "success";
    }

    return "fail";
  }




}
