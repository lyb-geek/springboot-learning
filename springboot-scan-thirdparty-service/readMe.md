## 本例主要演示如何把第三方服务注册到我们项目的spring容器中

> 方法一：利用@Import注解机制

> 方法二：调用beanFactory.registerSingleton()

> 方法三：利用@ComponentScan扫描

该方案配合springboot的自动装配效果更佳

> 方法四：利用ClassPathScanningCandidateComponentProvider扫描器