package com.github.lybgeek;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.user.**.dao")
public class KafkaTestApplication implements ApplicationRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(KafkaTestApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder().username("test2")
                .email("test2@qq.com")
                .fullname("test2")
                .mobile("1350000001")
                .password("1234561")
                .build();
      userService.saveAndPush(user);
    }
}
