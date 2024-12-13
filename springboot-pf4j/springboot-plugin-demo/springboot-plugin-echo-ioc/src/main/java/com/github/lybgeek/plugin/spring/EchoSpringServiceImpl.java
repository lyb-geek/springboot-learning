package com.github.lybgeek.plugin.spring;


import com.github.lybgeek.echo.EchoService;
import com.github.lybgeek.plugin.spring.aop.annotation.MethodCostTime;
import com.github.lybgeek.plugin.spring.util.EchoHelpler;
import org.pf4j.Extension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Extension
public class EchoSpringServiceImpl implements EchoService {

    @Autowired
    private EchoHelpler echoHelpler;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    @MethodCostTime
    public String echo(String msg) {
        System.out.println("this is a spring plugin test.....");
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextLong(500));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "spring:" + echoHelpler.getEchoMsg(msg);
    }
}
