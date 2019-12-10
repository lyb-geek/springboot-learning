package com.github.lybgeek.swagger.intercepors.config;

import com.github.lybgeek.swagger.intercepors.AuthIntercepors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler("/static/**")
        .addResourceLocations("classpath:/static/");

  }

  @Bean
  public AuthIntercepors authIntercepors() {

    return new AuthIntercepors();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(authIntercepors()).addPathPatterns("/**")
        .excludePathPatterns("/static/**")
        .excludePathPatterns("/")
        .excludePathPatterns("/demo/*/content/**","/demo/content/**")
        .excludePathPatterns("/export/**")
        .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**","/swagger-ui.html","/doc.html","/docs.html");


  }
}
