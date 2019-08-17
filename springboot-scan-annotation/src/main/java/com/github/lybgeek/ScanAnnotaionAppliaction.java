package com.github.lybgeek;

import com.github.lybgeek.annotaiton.EnableBindLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.github.lybgeek.dao")
@EnableBindLog
@Slf4j
public class ScanAnnotaionAppliaction
{
    public static void main( String[] args )
    {
        SpringApplication.run(ScanAnnotaionAppliaction.class,args);
    }


}
