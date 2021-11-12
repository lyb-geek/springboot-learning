package com.github.lybgeek.test;


import com.github.lybgeek.circuitbreaker.annotation.EnableSpiCircuitBreaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = "classpath:/spi.xml")
@EnableSpiCircuitBreaker(basePackages = "com.github.lybgeek")
public class SpiTestXmlApplication {


    public static void main(String[] args) throws Exception{
        SpringApplication.run(SpiTestXmlApplication.class);
    }


}
