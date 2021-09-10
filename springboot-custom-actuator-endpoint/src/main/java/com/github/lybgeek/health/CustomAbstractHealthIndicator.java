package com.github.lybgeek.health;


import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component("otherCustom")
public class CustomAbstractHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        int errorCode = check();
        if (errorCode == 1) {
            builder.down().down().withDetail("Error Code", errorCode).build();
            return;
        }
        builder.up().build();

    }

    private int check() {
        // perform some specific health check
        return ThreadLocalRandom.current().nextInt(5);
    }
}
