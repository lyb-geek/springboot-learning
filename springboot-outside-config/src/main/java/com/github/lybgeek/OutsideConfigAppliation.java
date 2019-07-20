package com.github.lybgeek;

import com.github.lybgeek.model.Author;
import com.github.lybgeek.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class OutsideConfigAppliation implements ApplicationRunner {

    @Autowired
    private Author author;

    @Autowired
    private User user;

    @Value("#{author.name}")
    private String name;

    @Value("${author.url}")
    private String authorUrl;

    @Value("${author.hobby}")
    private String authorHobby;
    public static void main( String[] args )
    {
        SpringApplication.run(OutsideConfigAppliation.class,args);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(author);
        log.info("name:{}---authorUrl:{}---nickName:{}",name,authorUrl,author.getNickName());
        System.out.println(authorHobby);
        System.out.println(user);

    }
}
