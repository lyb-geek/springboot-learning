package com.github.lybgeek.test;


import com.github.lybgeek.BufferTriggerApplication;
import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.user.service.UserService;
import com.github.lybgeek.user.util.ConcurrentCall;
import com.github.lybgeek.user.util.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest(classes = BufferTriggerApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;


    @Autowired
    @Qualifier("userServiceBufferTriggerImpl")
    private UserService userServiceBufferTriggerImpl;





    @Test
    public void testRegisterUserByCommon() throws IOException {
      new ConcurrentCall(20).run(()->{
          UserDTO user = UserUtil.generateUser();
          return userServiceImpl.register(user);
      });
    }

    @Test
    public void testRegisterUserByBufferTrigger() throws IOException {
        new ConcurrentCall(20).run(()->{
            UserDTO user = UserUtil.generateUser();
            return userServiceBufferTriggerImpl.register(user);
        });
    }
}
