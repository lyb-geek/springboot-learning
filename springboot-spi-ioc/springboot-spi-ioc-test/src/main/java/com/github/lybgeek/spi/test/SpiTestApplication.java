package com.github.lybgeek.spi.test;


import com.github.lybgeek.spi.framework.anotation.EnableSpi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpi(basePackages = "com.github.lybgeek.spi")
public class SpiTestApplication  {



    public static void main(String[] args) {
        SpringApplication.run(SpiTestApplication.class);
    }


}
