本示例主要演示多注册中心切换

### 默认情况下，如果项目引入多个注册中心依赖，会报

```java
Field registration in org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration$ServiceRegistryEndpointConfiguration required a single bean, but 2 were found:
	- nacosRegistration: defined by method 'nacosRegistration' in class path resource [com/alibaba/cloud/nacos/NacosDiscoveryAutoConfiguration.class]
	- eurekaRegistration: defined in BeanDefinition defined in class path resource [org/springframework/cloud/netflix/eureka/EurekaClientAutoConfiguration$RefreshableEurekaClientConfiguration.class]

```

解决方案，

方案一：利用自动装配的过滤器来解决org.springframework.boot.autoconfigure.AutoConfigurationImportFilter

步骤如下：

> 1、自定义类实现AutoConfigurationImportFilter接口，并重写match方法

具体可以查看com.github.lybgeek.registration.autoconfigure.filter.RegistrationCenterAutoConfigurationImportFilter

> 2、在指定spring.factories如下

org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=\
com.github.lybgeek.registration.autoconfigure.filter.RegistrationCenterAutoConfigurationImportFilter


方案二：把注册中心的配置地址信息单独拎出来

比如nacos配置文件信息单独放在application-nacos.yml，并在这个配置文件里禁用其他注册中心的客户端自动注册功能


