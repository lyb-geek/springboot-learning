package com.github.lybgeek.spi.test;


import com.github.lybgeek.dialect.SqlDialect;
import com.github.lybgeek.dialect.mysql.MysqlDialect;
import com.github.lybgeek.dialect.oracle.OracleDialect;
import com.github.lybgeek.spi.extension.ExtensionLoader;
import com.github.lybgeek.spi.factory.ExtensionFactory;
import com.github.lybgeek.spi.factory.SpiExtensionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class SpiTest {

    @Test
    public void testSpi(){
        SqlDialect sqlDialect = ExtensionLoader.getExtensionLoader(SqlDialect.class).getActivate("oracle");
        Assert.assertEquals("oracle",sqlDialect.dialect());
    }

    @Test
    public void testSpiOne(){
        SqlDialect sqlDialect = new SpiExtensionFactory().getExtension("oracle",SqlDialect.class);
        Assert.assertEquals("oracle",sqlDialect.dialect());
    }

    @Test
    public void testSpiTwo(){
        SqlDialect sqlDialect = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getActivate("spi").getExtension("mysql",SqlDialect.class);
        Assert.assertEquals("mysql",sqlDialect.dialect());
    }

    @Test
    public void testSpiAll(){
        Map<String, Class<?>> sqlDialectMap = ExtensionLoader.getExtensionLoader(SqlDialect.class).getExtensionClasses();
        Assert.assertTrue(sqlDialectMap.values().containsAll(Arrays.asList(OracleDialect.class,MysqlDialect.class)));
    }
}
