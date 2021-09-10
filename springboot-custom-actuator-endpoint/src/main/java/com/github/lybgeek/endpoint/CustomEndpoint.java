package com.github.lybgeek.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "customEndpoint")
public class CustomEndpoint {

  // @ReadOperation 对应GET请求

  /**
   * 请求示例：
   * GET http://localhost:8080/actuator/customEndpoint/zhangsan/20
   * @param username
   * @param age
   *
   * @return
   */
  @ReadOperation
  public Map<String, Object> endpointByGet(@Selector String username,@Selector Integer age) {
    Map<String, Object> customMap = new HashMap<>();
    customMap.put("httpMethod", HttpMethod.GET.toString());
    customMap.put("username",username);
    customMap.put("age",age);
    return customMap;
  }


  // @WriteOperation 对应POST请求

  /**
   * 请求示例：
   * POST http://localhost:8080/actuator/customEndpoint
   *
   * 请求参数为json格式
   *
   * {
   *     "username": "zhangsan",
   *     "age": 20
   * }
   *
   * @param username 参数都为必填项
   * @param age 参数都为必填项
   * @return
   */
  @WriteOperation
  public Map<String, Object> endpointByPost(String username,Integer age) {
    Map<String, Object> customMap = new HashMap<>();
    customMap.put("httpMethod", HttpMethod.POST.toString());
    customMap.put("username",username);
    customMap.put("age",age);
    return customMap;
  }


  // @DeleteOperation 对应Delete请求

  /**
   * 请求示例：
   * DELETE http://localhost:8080/actuator/customEndpoint
   *
   * @return
   */
  @DeleteOperation
  public Map<String, Object> endpointByDelete() {
    Map<String, Object> customMap = new HashMap<>();
    customMap.put("httpMethod", HttpMethod.DELETE.toString());

    return customMap;
  }
}