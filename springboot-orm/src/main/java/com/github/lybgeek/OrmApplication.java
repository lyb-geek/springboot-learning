package com.github.lybgeek;

import com.github.lybgeek.orm.jpa.common.repository.CustomSimpleJpaRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.github.lybgeek.orm.jpa.repository"},repositoryBaseClass = CustomSimpleJpaRepository.class)
@MapperScan(basePackages = {"com.github.lybgeek.orm.mybatisplus.dao"})
public class OrmApplication {
    public static void main( String[] args ) {

        SpringApplication.run(OrmApplication.class,args);
    }
}
