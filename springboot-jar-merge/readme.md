## 本示例主要演示如何避免多个jar通过maven打包成一个jar，多个同名配置文件发生覆盖问题

> 核心插件：maven-shade-plugin

利用：org.apache.maven.plugins.shade.resource.AppendingTransformer来处理处理多个jar包中存在重名的配置文件的合并

<configuration>
	<transformers>
		<transformer
			implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
			<resource>META-INF/spring.handlers</resource>
		</transformer>
		<transformer
			implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
			<resource>META-INF/spring.schemas</resource>
		</transformer>
	</transformers>
</configuration>

示例插件引用地方在：springboot-http/pom.xml

参考配置示例：https://github.com/apache/dubbo/blob/master/dubbo-all/pom.xml

更多例子：https://maven.apache.org/plugins/maven-shade-plugin/examples/resource-transformers.html

> 附带插件：flatten-maven-plugin

用来出来处理版本占位符

参考配置示例：https://github.com/apache/dubbo/blob/master/pom.xml

