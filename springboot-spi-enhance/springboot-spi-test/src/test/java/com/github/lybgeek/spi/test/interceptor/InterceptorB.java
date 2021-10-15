package com.github.lybgeek.spi.test.interceptor;


import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.Plugin;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;
import com.github.lybgeek.spi.test.interceptor.service.HelloServiceA;
import com.github.lybgeek.spi.test.interceptor.service.HelloServiceB;
import com.github.lybgeek.spi.test.interceptor.service.impl.HelloServiceCImpl;


@Intercepts({@Signature(type = HelloServiceA.class,method = "hello",args = {}),
@Signature(type = HelloServiceB.class,method = "hi",args = {String.class})})
public class InterceptorB implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("InterceptorB....");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 当走Plugin.wrap拦截器生效
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if(target.toString().startsWith(HelloServiceCImpl.class.getName())){
            return target;
        }

        return Plugin.wrap(target,this);
    }
}
