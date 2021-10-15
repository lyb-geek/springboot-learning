package com.github.lybgeek.spi.test.interceptor.dialect;

import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.model.InterceptorChain;
import com.github.lybgeek.interceptor.model.Invocation;
import com.github.lybgeek.spi.extension.ExtensionLoader;
import com.github.lybgeek.spi.factory.ExtensionFactory;
import com.github.lybgeek.spi.factory.InterceptorExtensionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DialectInterceptorTest {

    private InterceptorExtensionFactory factory;

    @Before
    public void before(){
        InterceptorChain chain = new InterceptorChain();
        chain.addInterceptor(new DialectInterceptor()).addInterceptor(new MysqlDialectInterceptor()).addInterceptor(new OracleDialectInterceptor());
        factory = (InterceptorExtensionFactory) (ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getActivate("interceptor"));
        factory.setChain(chain);
    }

    @Test
    public void testMysqlDialectInterceptor(){
        SqlDialect dialect = factory.getExtension("mysql",SqlDialect.class);
        Assert.assertEquals("mysql",dialect.dialect());
    }

    @Test
    public void testOracleDialectInterceptor(){
        SqlDialect dialect = factory.getExtension("oracle",SqlDialect.class);
        Assert.assertEquals("oracle",dialect.dialect());
    }
}
