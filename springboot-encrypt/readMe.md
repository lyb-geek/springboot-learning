## 本示例主要演示如何利用ClassFinal对类进行加密

官方地址：https://gitee.com/roseboy/classfinal

执行步骤：

> 1、在项目的pom引入classFinal插件

<plugin>
    <groupId>net.roseboy</groupId>
    <artifactId>classfinal-maven-plugin</artifactId>
    <version>1.2.1</version>
    <configuration>
        <password>123456</password>               <!--加密密码，如果是#号，则使用无密码模式加密-->
        <packages>*</packages>                              <!--加密的包名(可为空,多个用","分割)-->
        <cfgfiles>*.yml</cfgfiles>                          <!--需要加密的配置文件，一般是classes目录下的yml或properties文件(可为空,多个用","分割)-->
        <libjars>oneclickedcrypt-1.0-SNAPSHOT.jar</libjars> <!--jar/war包lib下要加密jar文件名(可为空,多个用","分割)-->
        <!--<cfgpasspath>/etc/.env</cfgpasspath>-->         <!--密码文件路径，可为空，默认 /etc/.env-->
        <!--<excludes>com.Test</excludes>-->                <!--排除不需要加密的文件-->
        <!--<classpath></classpath>-->                      <!--外部依赖jarlib-->
        <!--<debug>true</debug>-->                          <!--调试模式，打印debug日志-->
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>classFinal</goal>
            </goals>
        </execution>
    </executions>
</plugin>

> 2、运行mvn package时会在target下自动加密生成yourpaoject-encrypted


> 3、启动加密后的jar
   加密后的项目需要设置javaagent来启动，项目在启动过程中解密class，完全内存解密，不留下任何解密后的文件。

   解密功能已经自动加入到 yourpaoject-encrypted.jar中，所以启动时-javaagent与-jar相同，不需要额外的jar包。

   启动jar项目执行以下命令：

   java -javaagent:yourpaoject-encrypted.jar='-pwd 0000000' -jar yourpaoject-encrypted.jar


   //参数说明
   // -pwd      加密项目的密码
   // -pwdname  环境变量中密码的名字


   或者不加pwd参数直接启动，启动后在控制台里输入密码，推荐使用这种方式：

   java -javaagent:yourpaoject-encrypted.jar -jar yourpaoject-encrypted.jar


另外一款加密工具：class-winter https://gitee.com/JustryDeng/class-winter

