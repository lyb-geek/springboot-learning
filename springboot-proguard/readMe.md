## 本示例如要演示如何通过allatori进行混淆

allatori官方文档链接

http://www.allatori.com/doc.html


## 细节点

如果是springboot项目，如果在pom直接指定springboot parent

```xml
 <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.6.RELEASE</version>
    </parent>
```
则会使allatori.xml

```xml
<input>
    <jar in="${project.build.finalName}.jar" out="${project.build.finalName}.jar"/>
</input>
```
中的${project.build.finalName}直接是以字符串方式输出，而不会替换成相应的模块名

其原因是
当使用spring-boot-starter-parent时，maven resources filter将失效，
需要将${var}修改为@var@,过滤功能即可恢复

![](https://img2020.cnblogs.com/blog/1180389/202201/1180389-20220115114506187-53158166.png)

具体原因链接
https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-1.3-Release-Notes#maven-resources-filtering



修复的方法一：

pom.xml直接改成


```xml
<dependencyManagement>
        <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        </dependencies>
    </dependencyManagement>

```


方法二：

将${project.build.finalName}.jar改成@project.build.finalName@.jar

方法三：将覆盖maven-resources-plugin属性，将spring-boot-starter-parent内maven-resources-plugin
的maven-resources-plugin的<useDefaultDelimiters>false</useDefaultDelimiters>改成true。
此时${project.build.finalName}就能再次生效


