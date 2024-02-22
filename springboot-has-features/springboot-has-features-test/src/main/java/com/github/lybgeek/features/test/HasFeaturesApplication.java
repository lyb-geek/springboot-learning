package com.github.lybgeek.features.test;

import com.github.lybgeek.features.test.util.PrintUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.client.actuator.FeaturesEndpoint;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HasFeaturesApplication {



    public static void main(String[] args) {
        SpringApplication.run(HasFeaturesApplication.class);
    }

    @Autowired
    private InetUtils inetUtils;

    @EventListener
    public void listener(WebServerInitializedEvent webServerInitializedEvent){
       int port = webServerInitializedEvent.getWebServer().getPort();
       String ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();

       String featuresUrl = "http://" + ip + ":" + port + "/actuator/features";

        String result = new RestTemplate().getForObject(featuresUrl, String.class);
        PrintUtils.print(result);

    }


}
