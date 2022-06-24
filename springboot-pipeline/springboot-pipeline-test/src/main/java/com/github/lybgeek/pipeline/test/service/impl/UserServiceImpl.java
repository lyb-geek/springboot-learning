package com.github.lybgeek.pipeline.test.service.impl;


import com.github.lybgeek.pipeline.ChannelPipelineExecutor;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.test.handler.*;
import com.github.lybgeek.pipeline.test.model.User;
import com.github.lybgeek.pipeline.test.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean save(User user) {
       return ChannelPipelineExecutor.pipeline()
                .addLast(new UserCheckChannelHandler())
                .addLast(new UserFillUsernameAndEmailChannelHandler())
                .addLast(new UserPwdEncryptChannelHandler())
                .addLast(new UserMockSaveChannelHandler())
                .addLast(new UserPrintChannleHandler())
                .start(ChannelHandlerRequest.builder().params(user).build());
    }
}
