package com.github.lybgeek.transcase.accessperm;


import com.github.lybgeek.Application;
import com.github.lybgeek.user.util.UserUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TranInvalidCaseWithAccessPermTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        TranInvalidCaseWithAccessPerm tranInvalidCaseWithAccessPerm = context.getBean(TranInvalidCaseWithAccessPerm.class);
        boolean isSuccess = tranInvalidCaseWithAccessPerm.save(UserUtils.getUser());

        System.out.println(isSuccess);

    }
}
