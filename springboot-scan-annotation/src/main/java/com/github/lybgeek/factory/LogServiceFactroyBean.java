package com.github.lybgeek.factory;

import com.github.lybgeek.proxy.JdkServiceProxy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogServiceFactroyBean<T> implements FactoryBean<T> {

    private Class logClz;


    @Override
    public T getObject() throws Exception {
       // return (T) new SpringServiceProxy().getInstance(logClz);
       // return (T) new ServiceProxy().getInstance(logClz);
        return (T) new JdkServiceProxy(logClz).getInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return logClz;
    }
}
