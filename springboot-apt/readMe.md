## 本示例主要演示如何利用APT提高spring依赖注入BEAN的性能

实现APT的核心步骤

1、实现javax.annotation.processing.Processor接口，不过通常继承javax.annotation.processing.AbstractProcessor抽象类

2、核心方法

javax.annotation.processing.AbstractProcessor.process这个方法
主要是用来生成class模板文件或者一些配置文件比如SPI文件或者spring.fatctories文件
通过roundEnv.processingOver()来判断是否处理结束

javax.annotation.processing.AbstractProcessor.getSupportedAnnotationTypes这个方法用来指定要处理哪些注解

注解通常指定为@Retention(RetentionPolicy.SOURCE)，仅保留在源级别中，编译器将Java文件编译成class文件时将之遗弃


javax.annotation.processing.AbstractProcessor.getSupportedSourceVersion这个方法用来指定支持哪些JDK版本

javax.annotation.processing.AbstractProcessor.init这个初始方法可以用来获取一些资源
比如Filer

3、Element有哪些类型

 * ExecutableElement：表示某个类或接口的方法、构造方法或初始化程序（静态或实例），包括注解类型元素；
 * PackageElement：表示一个包程序元素；
 * TypeElement：表示一个类或接口程序元素；
 * TypeParameterElement：表示一般类、接口、方法或构造方法元素的形式类型参数；
 * VariableElement：表示一个字段、enum常量、方法或构造方法参数、局部变量或异常参数


