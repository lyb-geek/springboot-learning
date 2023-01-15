package com.github.lybgeek.jackson.env;


import com.github.lybgeek.jackson.properties.CustomJacksonFormatProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 在springcloud场景中，如果属性是配置在application.yml则会获取不到CUSTOM_JSON_FORMAT_ENABLE_KEY的值
 *
 * 推荐用{@link org.springframework.context.ApplicationContextInitializer}
 */
@Deprecated
@EnableConfigurationProperties(CustomJacksonFormatProperties.class)
public class CustomJacksonFormatEnvironmentPostProcessor implements EnvironmentPostProcessor {



    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
//        EnvUtils.postProcessEnvironment(environment);

    }


}
