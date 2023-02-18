## 本示例主要演示如何利用spring-plugin-core来实现策略模式

注：如果是springboot项目，默认就已经引入spring-plugin-core包。如果在运行的时候出现

java.lang.NoSuchMethodError: org.springframework.plugin.core.PluginRegistry.getPluginFor(Ljava/lang/Object;)Ljava/util/Optional;

这主要是因为jar冲突引起的，springboot 2.2+以下版本默认引入的版本是1.2.0，该版本是没提供Optional返回值，因此需要把版本提高到2.0.0+


使用spring-plugin-core来实现策略模式步骤

> 1、在项目中引入spring-plugin-core GAV

```xml
 <dependency>
            <groupId>org.springframework.plugin</groupId>
            <artifactId>spring-plugin-core</artifactId>
        </dependency>

```

> 2、定义一个实体类，这个实体类后边插件绑定插件类型会用到

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


> 3、定义插件实现org.springframework.plugin.core.Plugin接口

```java
public interface SmsPlugin extends Plugin<SmsRequest> {


    SmsResponse sendSms(SmsRequest smsRequest);


}

```


> 4、配置激活插件

```java
@EnablePluginRegistries(SmsPlugin.class)
@Configuration
public class SmsPluginActiveConfig {

}

```

> 5、定义插件的具体实现类

```java
@Component
public class AliyunSmsPlugin implements SmsPlugin {
    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        System.out.println("来自阿里云短信：" + smsRequest);
        return SmsResponse.builder()
                .code("200").message("发送成功")
                .success(true).result("阿里云短信的回执").build();
    }

    @Override
    public boolean supports(SmsRequest smsRequest) {
        return SmsType.ALIYUN == smsRequest.getSmsType();
    }
}

```

注：该具体插件必须是spring的bean

> 6、插件使用

在业务项目注入

 @Autowired
private PluginRegistry<SmsPlugin,SmsRequest> pluginRegistry;

通用调用pluginRegistry.getPluginFor方法拿到具体插件

