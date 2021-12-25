## 本例主要演示如何利用自定义注解整合springmvc+alibaba sentinel实现http熔断降级

## 实现核心逻辑

### 1、mvc部分
> 1、重写RequestMappingHandlerMapping

> 2、通过WebMvcRegistrations替换springmvc自带的RequestMappingHandlerMapping

### 2、熔断降级部分

> 1、替换原来SentinelResource注解的切面逻辑

> 2、把原先sentinelFilter的部分逻辑移植到aop切面进行实现，主要实现默认熔断降级页面渲染以及授权规则实现


本文实现的版本是基于spring-cloud-starter-alibaba-sentinel 2.1.0版本进行实现，实现的原因是为了解决之前
sentinel热点参数需要还要加SentinelResource注解才能实现，以及如果定义了全局异常处理，降级无法统计异常问题


