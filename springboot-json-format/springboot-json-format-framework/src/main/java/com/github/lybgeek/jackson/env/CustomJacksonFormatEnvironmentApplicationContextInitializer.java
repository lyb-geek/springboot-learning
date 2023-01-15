package com.github.lybgeek.jackson.env;

import com.github.lybgeek.jackson.env.util.EnvUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


public class CustomJacksonFormatEnvironmentApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        EnvUtils.postProcessEnvironment(environment);

    }
}