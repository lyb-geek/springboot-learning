package com.github.lybgeek.el.test;


import com.github.lybgeek.el.test.model.User;
import com.github.lybgeek.el.test.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = ElApplication.class)
@RunWith(SpringRunner.class)
public class ElApplicationTest {

    @Autowired
    private UserService userService;


    @Test
    public void testVip(){
        List<User> list = userService.getUserList(User.builder().userType("vip").id(1L).build());
        Assert.assertTrue(list.size() > 0);
        System.out.println(list);
    }


    @Test
    public void testNotVip(){
        List<User> list = userService.getUserList(User.builder().userType("notVip").id(1L).build());
        Assert.assertTrue(list.size() > 0);
        System.out.println(list);
    }


    @Test
    public void testSpecialId(){
        List<User> list = userService.getUserList(User.builder().userType("common").id(4L).build());
        Assert.assertTrue(list.size() > 0);
        System.out.println(list);
    }
}
