package com.github.lybgeek.db.check.test.user.controller;




import com.github.lybgeek.check.context.MySQLCommunicationsHolder;
import com.github.lybgeek.db.check.test.user.entity.User;
import com.github.lybgeek.db.check.test.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;



    @GetMapping("list")
    public List<User> list(){
        if(MySQLCommunicationsHolder.isMySQLCommunicationsException()){
            return defaultUsers();
        }
        try {
            return userService.list();
        } catch (Exception e) {

        }
        return defaultUsers();

    }

    private List<User> defaultUsers(){
        List<User> users = new ArrayList<>();
        User user = User.builder().email("defaultUser@qq.com")
                .fullname("默认用户")
                .username("defaultUser")
                .mobile("13600000011")
                .password("123456")
                .id(-1)
                .build();
        users.add(user);
        return users;
    }



}
