package com.github.lybgeek.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        Properties properties = new Properties();

        try {
            properties.load(new InputStreamReader(CustomEnvironmentPostProcessor.class.getClassLoader().getResourceAsStream("custom.properties"),"UTF-8"));

            PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource("custom",properties);
            environment.getPropertySources().addLast(propertiesPropertySource);
        } catch (IOException e) {
          log.error(e.getMessage(),e);
        }

    }
}
