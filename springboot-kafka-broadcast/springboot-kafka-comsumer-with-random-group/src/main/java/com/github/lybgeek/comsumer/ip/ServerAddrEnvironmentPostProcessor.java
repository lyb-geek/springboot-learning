package com.github.lybgeek.comsumer.ip;


import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ServerAddrEnvironmentPostProcessor implements EnvironmentPostProcessor{



    private String SERVER_ADDRESS = "server.addr";

    @Override
    @SneakyThrows
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> source = new HashMap<>();
        String serverAddr = InetAddress.getLocalHost().getHostAddress();
        source.put(SERVER_ADDRESS,serverAddr);
        MapPropertySource mapPropertySource = new MapPropertySource("serverAddrProperties",source);
        propertySources.addFirst(mapPropertySource);

    }



}
