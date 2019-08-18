本例主要演示springboot与一些主流orm框架的集成，比如jpa、mybatis、mybaitsplus

> 1、与jpa集成的功能点如下
  - 自动建表
  - 建表引擎改为InnoDB
  - 利用JpaSpecificationExecutor、JpaRepository来实现带复杂查询分页
  - 重写SimpleJpaRepository的save方法，使其按需更新空值属性
   (如果要更新的bean中的字段，存在null值，原生的SimpleJpaRepository进行更新操作时，会把null值更新进数据库)
   
> 2、与mybatisplus集成的功能点如下
  - 通过com.github.lybgeek.orm.mybatisplus.util.CodeGenerator 自动生成model、dao、service、controller模板代码
  - mybatisplus 分页

> 3、与mybatis集成的功能点如下
  - 通过mybatis-generator-maven-plugin和generatorConfig.xml配合自动生成model、dao、mapper.xml模板代码
  - mybaits分页
  - mybatis 插入或者更新时，通过自定义注解@CreateDate和@UpdateDate实现创建时间或者更新时间自动填充，而无需采用setCreateDate或者setUpdateDate的方式

> 4、其他
  - druid 连接池密码加密，可以利用本例代码中的com.github.lybgeek.orm.common.util.DruidEncryptPwdUtil进行加密
  - flyway 进行数据库版本管控
  - 利用dozerMapper/modelMapper进行DO和DTO的相互转换
 
 
  