package com.github.lybgeek.pipeline.spring.test.yml.service.impl;


import com.github.lybgeek.pipeline.ChannelPipeline;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.model.PipelineDefinition;
import com.github.lybgeek.pipeline.spring.test.model.User;
import com.github.lybgeek.pipeline.spring.test.yml.service.UserYmlService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserYmlServiceImpl implements UserYmlService {


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public boolean save(User user) {

        ChannelPipeline pipeline =  applicationContext.getBean(ChannelPipeline.class,PipelineDefinition.PREFIX + StringUtils.uncapitalize(UserYmlService.class.getSimpleName()));

        return pipeline.start(ChannelHandlerRequest.builder().params(user).build());
    }
}
