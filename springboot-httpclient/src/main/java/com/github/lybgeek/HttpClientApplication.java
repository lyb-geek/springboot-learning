package com.github.lybgeek;

import com.github.lybgeek.httpclient.annotation.EnableHttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
@EnableHttpClients(basePackages = "com.github.lybgeek")
public class HttpClientApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(HttpClientApplication.class,args);
    }
}
