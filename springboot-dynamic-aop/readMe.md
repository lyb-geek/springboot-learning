## 本示例主要演示如何实现可插拔AOP

核心点：要理解advised、advisor的概念，利用pointcut.getMethodMatcher().matches()来找到需要增强的bean，并将该bean手动转成advised(可通过ProxyFactory实现)

测试：先将springboot-aop-plugin 打包一下，如果有自己搭建nexus，可以通过上传到nexus maven私仓库进行验证，如果没有，就直接通过jar验证