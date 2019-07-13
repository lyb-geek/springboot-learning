package com.github.lybgeek.controller;

import com.github.lybgeek.dto.JdbcConfigDTO;
import com.github.lybgeek.service.JdbcConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JdbcConfigController {

    @Autowired
    private JdbcConfigService jdbcConfigService;

    @PostMapping(value="/refresh")
    public String refreshJdbcConfig(JdbcConfigDTO jdbcConfigDTO){
       return jdbcConfigService.refreshJdbcConfig(jdbcConfigDTO);
    }


}
