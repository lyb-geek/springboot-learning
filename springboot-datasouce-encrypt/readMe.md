## 本示例主要演示springboot数据源的加密

> 1、利用jasypt实现加密

这边有个坑点要注意，当选用jasypt 3.0.0-3.0.3版本会导致nocas或者apollo动态刷新失效
避坑点就可以采用3.0以下版本

> 2、利用druid的密码加密

druid目前仅支持对数据库密码的加解密

> 3、自定义实现

实现原理主要是利用spring的后置处理器进行实现