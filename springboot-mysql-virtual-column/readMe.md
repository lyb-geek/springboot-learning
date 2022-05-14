## 本示例主要演示mysql5.7版支持的json以及虚拟列

> 1、json相关文档

https://dev.mysql.com/doc/refman/5.7/en/json-functions.html

> 2、虚拟列

a、虚拟列不能做插入和更新
b、因为虚拟列不能做插入和更新，因此在mybatis-plus使用上，在虚拟列字段上要加
 @TableField(value = "v_user_name",insertStrategy = FieldStrategy.NEVER,updateStrategy = FieldStrategy.NEVER)
 注解

> mysql8 explain 新特性

通过 EXPLAIN ANALYZE SELECT  id,user_info,create_time,v_user_name AS username,v_date_month AS MONTH  FROM t_user_json     WHERE (v_user_name = 'cengwen')可以查看实际耗时
