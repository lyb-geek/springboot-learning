package com.github.lybgeek.client;


import com.github.lybgeek.api.EchoService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "feign-provider-env",path = EchoService.INTERFACE_NAME)
public interface EchoFeignClient extends EchoService {
}
