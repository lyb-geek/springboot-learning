package com.github.lybgeek.client;


import com.github.lybgeek.api.BarService;
import com.github.lybgeek.feign.factory.DynamicFeignClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class BarFeignClient {

    @Autowired
    private DynamicFeignClientFactory<BarService> dynamicFeignClientFactory;

    @Value("${feign.env}")
    private String env;

    public String bar(@PathVariable("username") String username){
        BarService barService = dynamicFeignClientFactory.getFeignClient(BarService.class,getBarServiceName());

        return barService.bar(username);
    }


    private String getBarServiceName(){
        return "feign-other-provider-" + env;
    }
}
