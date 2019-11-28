package com.github.lybgeek.docker.service;

public interface RedisService {

  boolean setValue(String key ,String value);

  String getValue(String key);

}
