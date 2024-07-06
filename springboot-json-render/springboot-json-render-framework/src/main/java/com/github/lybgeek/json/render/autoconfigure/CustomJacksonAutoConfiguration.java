package com.github.lybgeek.json.render.autoconfigure;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.lybgeek.json.render.enums.StatusEnums;
import com.github.lybgeek.json.render.enums.serialize.StatusEnumsJsonDerializer;
import com.github.lybgeek.json.render.enums.serialize.StatusEnumsJsonSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class CustomJacksonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer customJackson2ObjectMapperBuilderCustomizer(){
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
               jacksonObjectMapperBuilder
                       .serializerByType(Long.class,ToStringSerializer.instance)
                       .serializerByType(StatusEnums.class,new StatusEnumsJsonSerializer())
                       .deserializerByType(StatusEnums.class,new StatusEnumsJsonDerializer());
            }
        };

    }
}
