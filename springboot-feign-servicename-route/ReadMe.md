本示例主要演示如何根据环境动态指定feign调用服务名

> 方法一：主要变更FeignClientFactoryBean的name和url

核心点：本质就是在org.springframework.cloud.openfeign.FeignClientFactoryBean.getObject被调用之前，变更name和url；
利用spring的后置处理器BeanPostProcessor，可以达到这个效果

> 方法二：利用feign拦截器变更url

参考：https://blog.csdn.net/weixin_45357522/article/details/104020061

核心实现：com.github.lybgeek.feign.config.InterceptorConfig
          com.github.lybgeek.client.FooFeignClient
  

> 方法三：利用org.springframework.cloud.openfeign.FeignClientBuilder创建feign客户端
>
FeignClientBuilder创建feign客户端的效果和用@Feginclient注解的效果一样

参考：https://my.oschina.net/kaster/blog/4694238

核心实现：com.github.lybgeek.feign.factory.DynamicFeignClientFactory

