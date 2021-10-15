## 本例主要演示如何实现键值对SPI、带拦截器SPI、SPI与spring整合

### 1、实现键值对SPI核心逻辑

> 1、约定好要进行解析的目录，比如META-INF/services/

> 2、约定好要解析的文件名命名，比如

```java
com.github.lybgeek.dialect.SpringSqlDialect
```

> 3、约定好文件内容格式，比如

```properties
springMysql=com.github.lybgeek.dialect.mysql.SpringMysqlDialect
```

> 4、去约定好的目录，解析文件，并将相应内容放入缓存

> 5、根据key，去缓存查找相应的类实例


### 2、实现带拦截器的SPI核心流程

> 1、利用责任链+动态代理实现拦截器

参照mybatis拦截器实现

> 2、将拦截器绑定到SPI

### 3、SPI与spring集成

> 1、注解版

基于ClassPathBeanDefinitionScanner + ImportBeanDefinitionRegistrar 实现

> 2、xml版

通过自定义spring标签

  自定义spring标签步骤
 * 　  1、NamespaceHandler实现类处理自定义标签的处理器
 * 　　2、自定义解析BeanDefinitionParser解析器
 * 　　3、自定义标签
 * 　　4、spring.handlers、spring.schemas中写入处理器、标签的位置

 schema教程：https://www.w3school.com.cn/schema/index.asp



