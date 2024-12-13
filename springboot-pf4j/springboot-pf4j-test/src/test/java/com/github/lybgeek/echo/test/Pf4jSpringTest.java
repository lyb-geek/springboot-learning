package com.github.lybgeek.echo.test;


import com.github.lybgeek.echo.EchoService;
import com.github.lybgeek.echo.Pf4jApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest(classes = Pf4jApplication.class)
@RunWith(SpringRunner.class)
public class Pf4jSpringTest {


    @Autowired
    @Qualifier("echo-spring-plugin")
    private EchoService echoService;


    @Autowired
    private List<EchoService> echoServices;


    @Test
    public void testSpringPlugin(){
        System.out.println(echoService.echo("test123"));
    }


    @Test
    public void testSpringPlugins(){
        for (EchoService service : echoServices) {
            System.out.println(service.echo("lybgeek"));
        }

    }

}
