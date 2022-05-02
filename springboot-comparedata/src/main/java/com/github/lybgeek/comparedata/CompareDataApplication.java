package com.github.lybgeek.comparedata;


import com.github.lybgeek.comparedata.service.BaseCompareDataService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.comparedata.**.dao")
public class CompareDataApplication implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(CompareDataApplication.class);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, BaseCompareDataService> beanMap = applicationContext.getBeansOfType(BaseCompareDataService.class);

        for (BaseCompareDataService bean : beanMap.values()) {
            bean.compareAndSave();
        }

    }
}
