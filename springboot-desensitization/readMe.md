## 本文主要演示数据脱敏

> 1、基于Mybatis插件，在查询的时候针对特定的字段进行脱敏

> 2、基于Jackson，在序列化阶段对特定字段进行脱敏

> 3、自定义注解格式化

该实现没在本示例体现

主要实现步骤如下

- 1、实现AnnotationFormatterFactory接口

- 2、创建脱敏格式化类实现Formatter

- 3、将AnnotationFormatterFactory实现的接口注册到FormatterRegistry 

该实现可以参考https://blog.csdn.net/qq_27081015/article/details/103295983

> 4、利用fastjson进行脱敏

该实现没在本示例体现

主要实现步骤如下

- 1、实现ValueFilter接口，在process进行脱敏

- 2、配置fastjson为默认JSON转换

```java
/**
     * 配置fastjson为默认JSON转换
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1.定义一个converters转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializeFilters(new ValueDesensitizeFilter());//添加自己写的拦截器
        // 3.在converter中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4.将converter赋值给HttpMessageConverter
        HttpMessageConverter<?> converter = fastConverter;
        // 5.返回HttpMessageConverters对象
        return new HttpMessageConverters(converter);
    }

```
该实现可以参考https://blog.csdn.net/qq_27081015/article/details/103297316

> 5、基于Sharding Sphere实现数据脱敏

该实现可以参考:https://jaskey.github.io/blog/2020/03/18/sharding-sphere-data-desensitization/


> 6、使用mybatis-mate(未来大概率会付费)

https://gitee.com/baomidou/mybatis-mate-examples

https://baomidou.com/guide/mybatis-mate.html