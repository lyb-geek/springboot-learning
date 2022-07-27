package com.github.lybgeek.test.cache;


import com.github.lybgeek.redis.ext.test.CacheAbleExtApplication;
import com.github.lybgeek.redis.ext.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = CacheAbleExtApplication.class)
@RunWith(SpringRunner.class)
public class CacheExtTest {

    @Autowired
    private UserService userService;




    @Test
    public void testCacheExpiredAndPreFreshByCustom() throws Exception{
        System.out.println(userService.getUserFromRedisByCustomAnno("1"));

    }


    @Test
    public void testCacheExpiredAndPreFreshByCustomWithUserName() throws Exception{
        System.out.println(userService.getUserFromRedisByCustomAnnoWithUserName("zhangsan"));

        TimeUnit.SECONDS.sleep(5);

        System.out.println("sleep 5 second :" + userService.getUserFromRedisByCustomAnnoWithUserName("zhangsan"));

        TimeUnit.SECONDS.sleep(10);

        System.out.println("sleep 10 second :" + userService.getUserFromRedisByCustomAnnoWithUserName("zhangsan"));

        TimeUnit.SECONDS.sleep(5);

        System.out.println("sleep 5 second :" + userService.getUserFromRedisByCustomAnnoWithUserName("zhangsan"));

    }


}
