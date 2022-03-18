## 本示例主要演示使用registerSignleton进行动态bean注入后，如何使AOP生效

### 使用registerSignleton无法进行AOP增强的原因

registerSignleton方法调用，是直接注入单例池，并不会经过AOP的后置处理器进行增强


### 解决方案

1、使用BeanDefinition注册
2、先手动调用AOP动态代理API（org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization），再使用registerSignleton注入动态代理