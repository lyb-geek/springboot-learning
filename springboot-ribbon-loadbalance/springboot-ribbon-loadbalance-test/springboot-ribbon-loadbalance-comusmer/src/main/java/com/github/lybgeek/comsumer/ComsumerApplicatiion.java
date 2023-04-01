package com.github.lybgeek.comsumer;


import com.github.lybgeek.loadbalance.LoadbalanceFactory;
import com.github.lybgeek.loadbalance.ServerChooser;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ComsumerApplicatiion implements ApplicationRunner {

    @Autowired
    private LoadbalanceFactory loadbalanceFactory;

    public static void main(String[] args) {
        SpringApplication.run(ComsumerApplicatiion.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ServerChooser serverChooser = ServerChooser.builder()
                .loadBalancer(loadbalanceFactory.getLoadBalancer("provider", "test"))
                .build();

        while(true){
            Server reachableServer = serverChooser.getServer("provider");
            if(reachableServer != null){
                System.out.println(reachableServer.getHostPort());
            }
            TimeUnit.SECONDS.sleep(1);

        }





    }
}
