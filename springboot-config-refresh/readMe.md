## 本示例主要演示springboot项目脱离配置中心，如何实现属性动态刷新

核心关键类：spring-cloud-context下的org.springframework.cloud.context.environment.EnvironmentManager



通过post请求/actuator/env

{
  "name":"xxxx",
  "value":"xxx"
}
进行修改。使用改端点的条件：management.endpoint.env.post.enabled=true，且暴露env端点

@see org.springframework.cloud.context.environment.WritableEnvironmentEndpointWebExtension