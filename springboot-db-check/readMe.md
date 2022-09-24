## 本示例主要演示mysql探活

核心点：利用DruidDataSource的ValidConnectionChecker检测有效连接
利用ExceptionSorter来说明mysql已经连接不上(该方案有问题)

后面自己写定时器进行检测

druid的相关配置可以查看如下文章

https://blog.csdn.net/zhuhaoyu6666/article/details/104217498