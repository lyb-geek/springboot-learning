## 本示例主要演示如何利用spring serviceLocator来实现策略模式/工厂模式




使用spring serviceLocator来实现策略模式步骤



> 1、定义一个实体类，这个实体类后边插件绑定插件类型会用到

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsRequest implements Serializable {

    private Map<String,Object> metaDatas;

    private String to;

    private String message;

    private SmsType smsType;


}
```


> 2、定义策略抽象接口

```java
public interface SmsProvider {


    SmsResponse sendSms(SmsRequest smsRequest);


}

```


> 3、定义策略抽象工厂，主要用来选择策略

```java
public interface SmsFactory {

    SmsProvider getProvider(SmsType smsType);
}


```

> 4、定义策略的具体实现类

```java
@Component
public class AliyunSmsProvider implements SmsProvider {
    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        System.out.println("来自阿里云短信：" + smsRequest);
        return SmsResponse.builder()
                .code("200").message("发送成功")
                .success(true).result("阿里云短信的回执").build();
    }


}


```

注：该具体插件必须是spring的bean

> 5、配置ServiceLocatorFactoryBean

```java
 @Bean
    @ConditionalOnMissingBean
    public FactoryBean smsFactory(){
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(SmsFactory.class);
        // spring beanName映射，自定义名称映射关系,
        Properties properties = new Properties();
        properties.setProperty(SmsType.ALIYUN.toString(),"aliyunSmsProvider");
        properties.setProperty(SmsType.TENCENT.toString(),"tencentSmsProvider");
        serviceLocatorFactoryBean.setServiceMappings(properties);
        return serviceLocatorFactoryBean;
    }

```
注：抽象工厂获取到策略实际上是要设置如下

```java
public interface SmsFactory {

    SmsProvider getProvider(String beanName);
}


```

通过beanName来找到具体的策略，不过我们可以通过

```java
   serviceLocatorFactoryBean.setServiceMappings(properties);
```
来实现业务类型-->beanName的映射

映射源码如下

``java
	private String tryGetBeanName(@Nullable Object[] args) {
			String beanName = "";
			if (args != null && args.length == 1 && args[0] != null) {
				beanName = args[0].toString();
			}
			// Look for explicit serviceId-to-beanName mappings.
			if (serviceMappings != null) {
				String mappedName = serviceMappings.getProperty(beanName);
				if (mappedName != null) {
					beanName = mappedName;
				}
			}
			return beanName;
		}

```
> 6、策略使用

在业务项目注入

 @Autowired
private SmsFactory smsFactory;

通用调用smsFactory.getProvider方法拿到具体策略

