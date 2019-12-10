package com.github.lybgeek;

import com.github.lybgeek.common.Application;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebNoneApplication
{
    public static void main( String[] args )
    {

//        SpringApplication.run(WebNoneApplication.class,args);
//        new SpringApplicationBuilder(WebNoneApplication.class)
//          .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
//          .bannerMode(Banner.Mode.OFF)
//          .run(args);
        Application.run(WebNoneApplication.class,args);
    }
}