package com.github.lybgeek.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerMapperConfig {

  @Bean
  public Mapper dozerMapper(){
      Mapper mapper = DozerBeanMapperBuilder.buildDefault();

   //   Mapper mapper = DozerBeanMapperBuilder.create().withMappingBuilder(beanMappingBuilder()).build();
      return mapper;

  }


  @Bean
  public BeanMappingBuilder beanMappingBuilder(){
    BeanMappingBuilder beanMappingBuilder = new BeanMappingBuilder() {
      @Override
      protected void configure() {

      }
    };
    return beanMappingBuilder;
  }

}
