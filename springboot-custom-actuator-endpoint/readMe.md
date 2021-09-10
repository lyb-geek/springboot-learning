## 本例主要演示自定义各种actuator常用端点

> 1、自定义health 


通过实现org.springframework.boot.actuate.health.HealthIndicator接口

或者通过继承org.springframework.boot.actuate.health.AbstractHealthIndicator

推荐使用继承AbstractHealthIndicator

yml作如下配置
```yml

management:
   endpoint:
      health:
        show-details: always
```
查看自定health详细信息


> 2、自定义info

通过实现org.springframework.boot.actuate.info.InfoContributor接口

> 3、自定义customEndpoint

主要通过@Endpoint注解 + @ReadOperation、@WriteOperation、@DeleteOperation注解

其中@DeleteOperation对应delete请求、@ReadOperation对应get请求、@WriteOperation对应post请求


进行测试时，需要暴露相应端点

```yml

management:
  endpoints:
    web:
      exposure:
        include: health,info,customEndpoint
```

也可以

```yml

management:
  endpoints:
    web:
      exposure:
        include: "*"

```

通过

http://localhost:8080/actuator/health

http://localhost:8080/actuator/info

http://localhost:8080/actuator/customEndpoint

进行查看

