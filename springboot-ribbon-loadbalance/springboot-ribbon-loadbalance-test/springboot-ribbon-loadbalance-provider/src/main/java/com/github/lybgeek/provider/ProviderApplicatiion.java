package com.github.lybgeek.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ProviderApplicatiion {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplicatiion.class);
    }

    @RestController
    @RequestMapping("/echo")
    class EchoController{

        @GetMapping(value = "{message}")
        public String echo(@PathVariable("message") String message){
            return "echo -> " + message;
        }

    }
}
