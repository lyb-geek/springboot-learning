package com.github.lybgeek.virtualcolumn.test;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.javafaker.Faker;
import com.github.lybgeek.virtualcolumn.user.entity.User;
import com.github.lybgeek.virtualcolumn.user.model.UserDTO;
import com.github.lybgeek.virtualcolumn.user.service.UserService;
import lombok.SneakyThrows;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @SneakyThrows
    @Test
    public void testAdd(){
        Faker faker = Faker.instance(Locale.CHINA);
        List<User> users = new ArrayList<>();
        Random random = new Random();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < 100; i++) {
            String fullName = faker.name().fullName();
            String username = PinyinHelper.toHanYuPinyinString(fullName, format);
            UserDTO userDTO = UserDTO.builder().age(random.nextInt(30))
                    .email(username + "@qq.com")
                    .username(username)
                    .fullname(fullName)
                    .mobile(faker.phoneNumber().phoneNumber())
                    .build();
            User user = User.builder().userInfo(JSON.toJSONString(userDTO))
                    .build();
            users.add(user);
        }

        boolean b = userService.saveBatch(users);

        Assert.assertTrue(b);

    }

    @Test
    public void testSelectByVirualName(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(new User());
        userQueryWrapper.eq(User.USER_NAME,"zhangyanbin");
        List<User> list = userService.list(userQueryWrapper);
        Assert.assertNotNull(list);
        System.out.println(list);
    }
}
