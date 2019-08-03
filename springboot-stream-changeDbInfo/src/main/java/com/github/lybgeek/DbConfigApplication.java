package com.github.lybgeek;

import com.github.lybgeek.binder.DbConfigStreamBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(DbConfigStreamBinder.class)
public class DbConfigApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(DbConfigApplication.class,args);
    }
}
