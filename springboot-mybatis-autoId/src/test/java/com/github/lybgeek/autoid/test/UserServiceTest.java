package com.github.lybgeek.autoid.test;

import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSave(){
        User user = new User();
        user.setUserName("张三");
        user.setCreateTime(new Date());
        user.setDeptName("产品部");
        user.setDomain("lybgeek.com");
        user.setEmail("zhangsan@lybgeek.com");
        user.setMobile("18800000006");
        user.setSex("男");
        boolean isOk = userService.save(user);

        Assert.isTrue(isOk,"添加出错");
    }


    @Test
    public void testSaveBatch(){
        List<User> users = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            User user = new User();
            user.setUserName("张三"+i);
            user.setCreateTime(new Date());
            user.setDeptName("产品部"+i);
            user.setDomain("lybgeek.com");
            user.setEmail("zhangsan"+i+"@lybgeek.com");
            user.setMobile("1680000000"+i);
            user.setSex(i % 2 == 0 ? "男" : "女");
            users.add(user);
        }

        boolean isOk = userService.saveBatch(users);

        Assert.isTrue(isOk,"添加出错");
    }
}
