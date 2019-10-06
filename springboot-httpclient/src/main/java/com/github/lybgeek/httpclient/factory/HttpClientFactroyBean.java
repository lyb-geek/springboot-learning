package com.github.lybgeek.httpclient.factory;

import com.github.lybgeek.httpclient.proxy.HttpClientProxy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpClientFactroyBean<T> implements FactoryBean<T> {

    private Class httpclientClz;


    @Override
    public T getObject() throws Exception {


        return (T) new HttpClientProxy().getInstance(httpclientClz);
    }

    @Override
    public Class<?> getObjectType() {
        return httpclientClz;
    }
}
