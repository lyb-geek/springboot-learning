## 本示例主要演示通过多种方式实现AOP

> 方法一：通过aspectj-maven-plugin插件在编译期进行织入

本示例利用别人重新封装的插件，而非Codehaus的官方提供的插件，Codehaus的官方提供的插件
只能支持JDK8（包含JDK8）以下的版本，而本示例的插件可以支持到JDK13

本示例的插件github地址：https://github.com/nickwongdev/aspectj-maven-plugin

Codehaus的官方插件地址：https://github.com/mojohaus/aspectj-maven-plugin
以及相应介绍：https://www.mojohaus.org/aspectj-maven-plugin/index.html

```xml
<build>
  <plugins>
    <plugin>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>2.22.2</version>
    </plugin>
    <plugin>
      <groupId>com.nickwongdev</groupId>
      <artifactId>aspectj-maven-plugin</artifactId>
      <version>1.12.6</version>
      <configuration>
        <complianceLevel>${java.version}</complianceLevel>
        <source>${java.version}</source>
        <target>${java.version}</target>
        <encoding>${project.encoding}</encoding>
      </configuration>
      <executions>
        <execution>
          <goals>
            <goal>compile</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
<dependencies>
  <dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.5</version>
  </dependency>
</dependencies>
```
通过执行maven命令 mvn clean compile

> 方法二：利用aspectjweaver在JVM进行类加载时进行织入

步骤一：
```xml
 <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.5</version>
        </dependency>
```

步骤二：创建切面类和需要被织入的目标类

具体看代码

步骤三：在src/main/resource目录下创建META-INF/aop.xml文件

```xml
<aspectj>
    <weaver options="-XnoInline -Xset:weaveJavaxPackages=true -Xlint:ignore -verbose -XmessageHandlerClass:org.springframework.aop.aspectj.AspectJWeaverMessageHandler">
        <!--在编织时导入切面类和需要被切入的目标类-->
        <include within="com.github.lybgeek.aop.aspect.EchoAspect"/>
        <include within="com.github.lybgeek.aop.service.EchoService"/>
    </weaver>
    <aspects>
        <!--指定切面类-->
        <aspect name="com.github.lybgeek.aop.aspect.EchoAspect"/>
    </aspects>
</aspectj>
```

步骤四：指定VM参数

```shell
-javaagent:aspectjweaver.jar的路径
示例：
-javaagent:D:\repository\org\aspectj\aspectjweaver\1.9.5\aspectjweaver-1.9.5.jar
```
或者也可以直接和spring-boot-maven-plugin插件整合,agent这个配置参数需要在spring 2.2.0+版本才有

示例：

```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.3.0.RELEASE</version>
                <configuration>
                    <agents>
                        <agent>
                            ${settings.localRepository}/org/aspectj/aspectjweaver/1.9.5/aspectjweaver-1.9.5.jar
                        </agent>
                    </agents>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

> 方法三：利用spring aop在运行时织入

这个烂大街了，省略

> 方法四：利用spring aop提供的原生API实现

原生的API有：AspectJProxyFactory、ProxyFactoryBean、ProxyFactory
例子查看测试类：com.github.lybgeek.aop.test.AopApiTest

> 方法五：利用APT + JavaPoet 在编译期实现切面逻辑(该方法只能生成java文件无法对原有java文件进行修改)

JavaPoet是JavaPoet 是生成 .java 源文件的 Java API，具体查看官方文档
https://github.com/square/javapoet
或者查看此博文https://weilu.blog.csdn.net/article/details/112429217

> 方法六：利用APT + AST 在编译器实现切面逻辑（利用AST语法树，可以在编译器对原有的java文件进行修改）

AST相关资料：

https://z.itpub.net/article/detail/8DC0190A2BDA5BC5EB8696A7A651779E
https://blog.csdn.net/u013998373/article/details/90050810
https://blog.csdn.net/a_zhenzhen/article/details/86065063

