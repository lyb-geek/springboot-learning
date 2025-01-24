package com.github.lybgeek.chain.test;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.CatalogBase;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

@Slf4j
public class ChainTest {

    private Context data;

    @Before
    public void prepareData(){
        data = new ContextBase();
        data.put("flyEnabled", "false");
        data.put("jumpEnabled", "false");
        data.put("runEnabled", "false");
    }



    @Test
    public void testChain(){

        Chain chain = new ChainBase();
        chain.addCommand(new FlyCommand());
        chain.addCommand(new JumpCommand());
        chain.addCommand(new RunCommand());

        try {
            chain.execute(data);
        } catch (Exception e) {
            log.error("执行链路失败",e);
        }

    }


    @Test
    public void testCatalog(){
        Catalog catalog = new CatalogBase();
        catalog.addCommand("fly", new FlyCommand());
        catalog.addCommand("jump", new JumpCommand());
        catalog.addCommand("run", new RunCommand());




        Iterator names = catalog.getNames();
        while (names.hasNext()) {
            try {
                String name = (String) names.next();
                catalog.getCommand(name).execute(data);
            } catch (Exception e) {
               log.error("执行链路失败",e);
            }
        }



    }
}
