## 



maven插件的方式，一种是以doc的方式，一种是以注解的方式

本例主要实现注解方式

> 1、pom引入

ps：指定pom为：<packaging>maven-plugin</packaging>

```xml
 <!-- dependencies to annotations -->
      <dependency>
        <groupId>org.apache.maven.plugin-tools</groupId>
        <artifactId>maven-plugin-annotations</artifactId>
        <version>3.5.2</version>
        <scope>provided</scope>
      </dependency>

```

> 2、建立插件类继承org.apache.maven.plugin.AbstractMojo

> 3、插件类上加上org.apache.maven.plugins.annotations.Mojo注解

ps：如果要指定参数，则加org.apache.maven.plugins.annotations.Parameter注解

如果使用文档方式，可以直接用maven生成，选择maven-archetype-mojo模板生成

官方文档例子

https://maven.apache.org/guides/plugin/guide-java-plugin-development.html

在制作插件时，可能会出现

```java
 org.apache.maven.plugins:maven-plugin-plugin:3.2:descriptor (default-descriptor)

```

异常

出错的原因是

由于没有指定maven-plugin-plugin版本，所以默认是3.2，不适配当前代码，所以指定版本，在pom文件中加上

```xml
   <build>
         <plugins>
             <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-plugin-plugin</artifactId>
                 <version>3.5</version>
             </plugin>
         </plugins>
     </build>

```
即可