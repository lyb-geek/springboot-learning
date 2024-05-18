package com.github.lybgeek.thirdparty.test;


import com.github.lybgeek.thirdparty.property.ThirdpartyBeanReplaceProperty;
import com.github.lybgeek.thirdparty.repository.ThirdpartyRepository;
import com.github.lybgeek.thirdparty.service.ThirdpartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ThirdpartyTestApplication implements ApplicationRunner {

    @Autowired
    private ThirdpartyService thirdpartyService;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ThirdpartyTestApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(thirdpartyService.getThirdparty());
        System.out.println(applicationContext.getBeansOfType(ThirdpartyRepository.class));
    }
}
