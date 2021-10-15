package com.github.lybgeek.test.controller;


import com.github.lybgeek.dialect.SpringSqlDialect;
import com.github.lybgeek.spring.spi.annatation.SpiAutowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class SpiTestController {


      @SpiAutowired(value = "hello-mysql")
//    @Autowired
    private SpringSqlDialect springSqlDialect;


    @RequestMapping(value = "sql")
    public String dialect(){
        return springSqlDialect.dialect();
    }
}
