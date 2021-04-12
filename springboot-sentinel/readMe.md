本文主要演示springboot整合alibaba sentinel各种降级规则遇到的问题
> 1、降级不生效问题

原因：项目引入了自定义全局异常com.github.lybgeek.common.exception.GlobalExceptionHandler，
而异常数的统计在com.alibaba.csp.sentinel.adapter.spring.webmvc.AbstractSentinelInterceptor.afterCompletion这个方法执行，
自定义全局异常的处理会先于com.alibaba.csp.sentinel.adapter.spring.webmvc.AbstractSentinelInterceptor.afterCompletion这个方法执行执行，
因为我们在全局异常里面已经对异常进行处理，转换为全局对象了，这样导致AbstractSentinelInterceptor.afterCompletion无法获取到异常，进而无法统计异常数或者异常比例

解决方法：参考https://github.com/alibaba/Sentinel/issues/1281或者https://github.com/alibaba/Sentinel/issues/404
当然可以直接引用本文的例子

> 2、授权规则不生效问题

原因：项目中没有实现com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser接口，导致无法解析请求来源

解决方法：新增一个请求来源解析器com.github.lybgeek.sentinel.authority.CustomRequestOriginParser，用来解析请求来源

> 3、热点规则不生效问题

原因：web埋点如果以url作为资源名，规则不生效
解决方法：以@SentinelResource注解定义的name作为资源名，参考：https://github.com/alibaba/Sentinel/issues/1734

热点规则配置@SentinelResource后，可能还会出现java.lang.reflect.UndeclaredThrowableException: null
解决方法：需要在方法中添加throws BlockException或添加blockHandler来处理异常
参考：https://github.com/alibaba/Sentinel/issues/776

## 新增基于拉模式的规则持久化存储

参考：http://www.itmuch.com/spring-cloud-alibaba/sentinel-rules-persistence-pull-mode


