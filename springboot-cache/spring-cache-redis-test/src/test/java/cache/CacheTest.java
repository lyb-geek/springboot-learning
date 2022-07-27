package cache;


import com.github.lybgeek.redis.test.CacheAbleApplication;
import com.github.lybgeek.redis.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = CacheAbleApplication.class)
@RunWith(SpringRunner.class)
public class CacheTest {

    @Autowired
    private UserService userService;


    @Test
    public void testCacheExpiredAndPreFresh() throws Exception{

        System.out.println(userService.getUserFromRedis("1"));

        TimeUnit.SECONDS.sleep(3);

        System.out.println("sleep 3 second :" + userService.getUserFromRedis("1"));

        TimeUnit.SECONDS.sleep(7);

        System.out.println("sleep 7 second :" + userService.getUserFromRedis("1"));

    }




}
