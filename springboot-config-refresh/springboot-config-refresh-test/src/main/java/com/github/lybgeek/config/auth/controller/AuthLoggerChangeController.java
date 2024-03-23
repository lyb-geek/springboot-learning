package com.github.lybgeek.config.auth.controller;


import com.github.lybgeek.config.auth.filter.AuthHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.lybgeek.config.auth.controller.AuthLoggerChangeController.BASE_LOG_URL;

@RestController
@RequestMapping(BASE_LOG_URL)
@RequiredArgsConstructor
public class AuthLoggerChangeController {

    public static final String BASE_LOG_URL = "logger";

    private final EnvironmentManager environmentManager;

    private final String AUTH_FILTER_LOGGER_CLASS_NAME = "logging.level." + AuthHandlerInterceptor.class.getName();


    @GetMapping("change/{level}")
    public String change(@PathVariable("level") String level){
        environmentManager.setProperty(AUTH_FILTER_LOGGER_CLASS_NAME,level);
        return "change logger success";
    }


}
