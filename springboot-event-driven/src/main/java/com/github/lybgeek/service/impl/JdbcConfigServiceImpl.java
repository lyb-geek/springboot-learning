package com.github.lybgeek.service.impl;

import com.github.dozermapper.core.Mapper;
import com.github.lybgeek.dto.JdbcConfigDTO;
import com.github.lybgeek.model.JdbcConfig;
import com.github.lybgeek.service.JdbcConfigService;
import com.github.lybgeek.util.CacheJdbcConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class JdbcConfigServiceImpl implements JdbcConfigService, ApplicationEventPublisherAware {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public String refreshJdbcConfig(JdbcConfigDTO jdbcConfigDTO) {
        JdbcConfig jdbcConfig = dozerMapper.map(jdbcConfigDTO,JdbcConfig.class);
        JdbcConfig cacheJdbcConfig = CacheJdbcConfigUtil.INSTANCE.getjdbcConfig();
        JdbcConfig changeFieldJdbcConfig = getChangeJdbcConfig(jdbcConfig, cacheJdbcConfig);

        if(StringUtils.isBlank(changeFieldJdbcConfig.getDriverClassName()) && StringUtils.isBlank(changeFieldJdbcConfig.getPassword())
                && StringUtils.isBlank(changeFieldJdbcConfig.getUrl())&& StringUtils.isBlank(changeFieldJdbcConfig.getUsername())){
            applicationEventPublisher.publishEvent("noChange");
            return "no change";
        }
        applicationEventPublisher.publishEvent(changeFieldJdbcConfig);
        return "refresh success";
    }

    private JdbcConfig getChangeJdbcConfig(JdbcConfig jdbcConfig, JdbcConfig cacheJdbcConfig) {
        JdbcConfig changeFieldJdbcConfig = new JdbcConfig();
        if(!cacheJdbcConfig.getDriverClassName().equals(jdbcConfig.getDriverClassName())){
            changeFieldJdbcConfig.setDriverClassName(jdbcConfig.getDriverClassName());
        }
        if(!cacheJdbcConfig.getPassword().equals(jdbcConfig.getPassword())){
            changeFieldJdbcConfig.setPassword(jdbcConfig.getPassword());
        }
        if(!cacheJdbcConfig.getUrl().equals(jdbcConfig.getUrl())){
            changeFieldJdbcConfig.setUrl(jdbcConfig.getUrl());
        }
        if(!cacheJdbcConfig.getUsername().equals(jdbcConfig.getUsername())){
            changeFieldJdbcConfig.setUsername(jdbcConfig.getUsername());
        }
        return changeFieldJdbcConfig;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
