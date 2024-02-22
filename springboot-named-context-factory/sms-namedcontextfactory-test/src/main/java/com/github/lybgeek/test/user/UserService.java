package com.github.lybgeek.test.user;


import com.github.lybgeek.test.user.event.UserRegisterEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ApplicationContext applicationContext;

    public static final String SERVICE_NAME = "userService";

    public void registerUser(String userName, String password,String mobile){
        System.out.println("注册用户"+userName+"成功");
        UserRegisterEvent event = new UserRegisterEvent(userName,password,mobile);
        applicationContext.publishEvent(event);
    }
}
