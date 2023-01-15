## 本例主要演示如何通过自定义jackson格式,解决比如nPrice渲染到前端变成nprice

核心实现类

通过自定义继承PropertyNamingStrategy

通过在yml文件配置

spring:
  jackson:
    property-naming-strategy: com.github.lybgeek.jackson.CustomPropertyNamingStrategy