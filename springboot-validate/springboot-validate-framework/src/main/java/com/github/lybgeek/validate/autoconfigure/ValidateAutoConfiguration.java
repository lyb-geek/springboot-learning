package com.github.lybgeek.validate.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;


@Configuration
@ComponentScan(basePackages = {"com.github.lybgeek.validate.constraint","com.github.lybgeek.validate.advice"})
public class ValidateAutoConfiguration implements WebMvcConfigurer {

    @Autowired
   private MessageSource messageSource;

    /**
     * @see <a href="https://github.com/spring-projects/spring-boot/pull/17530">...</a>
     * 在Spring Boot 2.5.x版本中以及之前，Spring Boot Validation默认只支持读取resources/ValidationMessages.properties系列文件的中的国际化属性，
     * 且中文需要进行ASCII转码才可正确显示，Spring Boot 2.6.x版本之后已支持验证注解message属性引用Spring Boot自身国际化配置。
     * @return
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        //仅兼容Spring Boot spring.messages设置的国际化文件和原hibernate-validator的国际化文件
        //不再支持resource/ValidationMessages.properties
        bean.setValidationMessageSource(this.messageSource);
        return bean;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 如果需要支持修改语言，则LocaleResolver要改成SessionLocaleResolver或者其他，不能用默认的
        // AcceptHeaderLocaleResolver
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    @Bean
    public LocaleResolver localeResolver() {
        // 默认AcceptHeaderLocaleResolver实现国际化
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }



}
