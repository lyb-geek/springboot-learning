package com.github.lybgeek.jackson.env.util;


import com.github.lybgeek.jackson.CustomPropertyNamingStrategy;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

import static com.github.lybgeek.jackson.properties.CustomJacksonFormatProperties.CUSTOM_JSON_FORMAT_ENABLE_KEY;

public final class EnvUtils {

    private EnvUtils(){}

    private static final String JACKSON_PROPERTY_NAMING_STRATEGY_KEY = "spring.jackson.property-naming-strategy";


    public static void postProcessEnvironment(ConfigurableEnvironment environment){
        String isCustomJsonFormatEnaled = environment.getProperty(CUSTOM_JSON_FORMAT_ENABLE_KEY,"true");
        if("true".equalsIgnoreCase(isCustomJsonFormatEnaled)){
            setCustomJacksonPropertyNamingStrategy(environment);
        }
    }

    private static void setCustomJacksonPropertyNamingStrategy(ConfigurableEnvironment environment) {
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> mapPropertySource = new HashMap<>();
        mapPropertySource.put(JACKSON_PROPERTY_NAMING_STRATEGY_KEY, CustomPropertyNamingStrategy.class.getName());
        PropertySource propertySource = new MapPropertySource("custom-json-format-properties",mapPropertySource);
        propertySources.addFirst(propertySource);
    }

}
