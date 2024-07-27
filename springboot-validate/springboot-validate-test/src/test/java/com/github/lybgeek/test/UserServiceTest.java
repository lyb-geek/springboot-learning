package com.github.lybgeek.test;


import cn.hutool.json.JSONUtil;
import com.dtflys.forest.Forest;
import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAddErrorWithI18n(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("12345");
        userDTO.setPassword("12345");
        userDTO.setMobile("123");
        System.out.println(JSONUtil.toJsonStr(userService.save(userDTO)));

    }

    @Test
    public void testUpdateErrorWithI18n(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("12345");
        userDTO.setPassword("12345");
        userDTO.setMobile("123");
        System.out.println(JSONUtil.toJsonStr(userService.update(userDTO)));

    }
}
