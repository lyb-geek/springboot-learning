package com.github.lybgeek.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feign-provider")
public interface FooFeignClient {

    @GetMapping(value = "//feign-provider-$env/foo/{username}")
    String foo(@PathVariable("username") String username);
}
