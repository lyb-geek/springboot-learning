package com.github.lybgeek;

import com.github.lybgeek.listenr.LogStreamBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableBinding(LogStreamBinder.class)
@EnableScheduling
@EnableAsync
public class LogApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(LogApplication.class,args);
    }

//  @Bean
//  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//    return args -> {
//
//      log.info("Let's inspect the beans provided by Spring Boot:");
//
//
//      String[] beanNames = ctx.getBeanDefinitionNames();
//      Arrays.sort(beanNames);
//      for (String beanName : beanNames) {
//        log.info(beanName);
//      }
//
//    };
//	}
}
