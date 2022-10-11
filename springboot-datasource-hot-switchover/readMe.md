## 本文主要演示apollo与spring AbstractRoutingDataSource 结合实现动态数据源热切换

# 实现的核心点

AbstractRoutingDataSource

druid创建连接池线程关闭

扩展ConfigurationPropertiesBinder进行属性绑定刷新
