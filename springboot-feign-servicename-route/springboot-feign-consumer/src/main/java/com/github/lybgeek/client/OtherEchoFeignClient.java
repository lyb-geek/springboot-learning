package com.github.lybgeek.client;


import com.github.lybgeek.api.OtherEchoService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "feign-other-provider-env",path = OtherEchoService.INTERFACE_NAME)
public interface OtherEchoFeignClient extends OtherEchoService {
}
