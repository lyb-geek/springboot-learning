package com.github.lybgeek.test;


import com.github.lybgeek.circuitbreaker.annotation.EnableSpiCircuitBreaker;
import com.github.lybgeek.dialect.SpringSqlDialect;
import com.github.lybgeek.spring.spi.annatation.EnableSpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
@EnableSpi(basePackages = "com.github.lybgeek")
@EnableSpiCircuitBreaker(basePackages = "com.github.lybgeek")
public class SpiTestApplication implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;


    public static void main(String[] args) throws Exception{
        SpringApplication.run(SpiTestApplication.class);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        applicationContext.getBeansOfType(SpringSqlDialect.class)
                .forEach((beanName,bean) -> System.out.println(beanName + "-->" + bean));
    }
}
