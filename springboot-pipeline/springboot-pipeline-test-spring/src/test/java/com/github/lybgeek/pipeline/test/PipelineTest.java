package com.github.lybgeek.pipeline.test;


import com.github.javafaker.Faker;
import com.github.lybgeek.pipeline.spring.test.SpringPipelineApplication;
import com.github.lybgeek.pipeline.spring.test.anotataion.service.UserService;
import com.github.lybgeek.pipeline.spring.test.model.User;
import com.github.lybgeek.pipeline.spring.test.xml.service.UserXmlService;
import com.github.lybgeek.pipeline.spring.test.yml.service.UserYmlService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

@SpringBootTest(classes = SpringPipelineApplication.class)
@RunWith(SpringRunner.class)
public class PipelineTest {



    @Autowired
    private UserYmlService userYmlService;

    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private UserXmlService userXmlService;


    @Before
    public void prepareData(){
        Faker faker = Faker.instance(Locale.CHINA);
        user = User.builder().age(20)
                .fullname(faker.name().fullName())
                .mobile(faker.phoneNumber().phoneNumber())
                .password("123456").build();
    }


    @Test
    public void testPipelineYml(){
        boolean isOk = userYmlService.save(user);
        Assert.assertTrue(isOk);

    }

    @Test
    public void testPipeline(){
        boolean isOk = userService.save(user);
        Assert.assertTrue(isOk);

    }

    @Test
    public void testPipelineXml(){
        boolean isOk = userXmlService.save(user);
        Assert.assertTrue(isOk);

    }
}
