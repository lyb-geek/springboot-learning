package com.github.lybgeek.gateway.dashboard;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GateWayMockDashboard implements ApplicationRunner {



    public static void main(String[] args) {
        SpringApplication.run(GateWayMockDashboard.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
