该demo主要演示长链接转短链接，并实现springboot与elasticserch7.3的整合，整合的功能如下
- 通过自定义@EsOperate注解来实现elasticsearch与mysql 数据增删改查同步
- 通过自定义注解@EnableCustomElasticsearchRepositories与@ElasticsearchRepository整合，实现简单版类似spring-data-elasticsearch的功能


长链接转短链接的实现原理

> 通过发号器来实现，本例以mysql作为发号器

- 1、用户输入要转换的长链接
- 2、数据库生成一条记录，记录的核心内容为自增id和长链接地址
- 3、数据库生成记录后，返回短链接给客户，其短链接格式为：域名+/数据库自增id的62进制
- 4、当用户通过短链接访问时，比如https://www.abc/efg(其中efg为数据库自增id的62进制)，
则要先把efg转换为十进制
- 5、再通过已经转换好的十进制，去数据库查找相应id对应的长链接
- 6、通过302跳转到查找到的长链接






