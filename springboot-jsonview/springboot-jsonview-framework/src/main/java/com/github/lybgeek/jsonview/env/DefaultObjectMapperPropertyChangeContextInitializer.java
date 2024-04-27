package com.github.lybgeek.jsonview.env;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static com.github.lybgeek.jsonview.property.JsonViewProperty.PREFIX;

public class DefaultObjectMapperPropertyChangeContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String ignoreNullPropertyValue = environment.getProperty(PREFIX + ".ignore-null-property-value", "true");
        String enabled = environment.getProperty(PREFIX + ".enabled", "true");
        if("false".equals(enabled)){
            return;
        }

        if("true".equals(ignoreNullPropertyValue)){
            environment.getSystemProperties().put("spring.jackson.default-property-inclusion", "non_null");
        }







    }
}
