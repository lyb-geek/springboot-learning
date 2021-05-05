## 本文主要演示两种低侵入方式进行日志记录

> 1、基于javaagent的方式进行记录

- 核心技术点：利用net.bytebuddy简化字节码操作
- 业务代码中如何使用

```
#在vm参数里面添加
 -javaagent:F:\springboot-learning\springboot-agent\springboot-javaagent-log\target\agent-log.jar=F:\springboot-learning\springboot-agent\springboot-javaagent-log\target\classes\agent.properties
```

> 2、基于AOP + springboot自动装配机制

- 核心技术点：利用org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor + org.aopalliance.intercept.MethodInterceptor 进行编程式切面拦截