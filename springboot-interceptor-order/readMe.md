本文主要演示如何让springboot拦截器的执行顺序按我们想要的顺序执行

（默认按照先注册先拦截）

## 实现核心点

利用registry.addInterceptor(customInterceptor2).order(1)
不指定order时，默认order为0。order顺序越小，越先执行