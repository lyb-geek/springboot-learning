package com.github.lybgeek.comparedata.test;


import com.github.javafaker.Faker;
import com.github.lybgeek.comparedata.mockuser.entity.MockUser;
import com.github.lybgeek.comparedata.mockuser.service.MockUserService;
import com.github.lybgeek.comparedata.user.entity.User;
import com.github.lybgeek.comparedata.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MockUserServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private MockUserService mockUserService;

    private Faker faker = Faker.instance(Locale.CHINA);


    @Test
    public void testAddUsers(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 300000; i++) {
            User user = User.builder()
                    .email("test" + i + "@qq.com")
                    .fullname("测试-"+i)
                    .mobile(faker.phoneNumber().phoneNumber())
                    .password("123456")
                    .username("test-"+i)
                    .build();
            users.add(user);
        }

        userService.saveBatch(users);

    }

    @Test
    public void testAddMockUsers(){
        List<MockUser> users = new ArrayList<>();
        for (int i = 0; i < 300000; i++) {
            MockUser user = MockUser.builder()
                    .email("test" + i + "@qq.com")
                    .fullname("测试-"+i)
                    .mobile(faker.phoneNumber().phoneNumber())
                    .password("123456")
                    .username("test-"+i)
                    .build();
            users.add(user);
        }

        mockUserService.saveBatch(users);

    }
}
