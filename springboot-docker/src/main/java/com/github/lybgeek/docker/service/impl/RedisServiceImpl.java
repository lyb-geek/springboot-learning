package com.github.lybgeek.docker.service.impl;

import com.github.lybgeek.common.util.RedisUtil;
import com.github.lybgeek.docker.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

  @Autowired
  private RedisUtil redisUtil;

  @Override
  public boolean setValue(String key, String value) {
    return  redisUtil.set(key,value);
  }

  @Override
  public String getValue(String key) {

    return String.valueOf(redisUtil.get(key));
  }
}
