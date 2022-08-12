package com.github.lybgeek.interceptor.autoconfigure;


import com.github.lybgeek.advice.ProductRequestBodyAdvice;
import com.github.lybgeek.interceptor.OrderFilter;
import com.github.lybgeek.interceptor.OrderHandlerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackageClasses = OrderFilter.class)
public class OrderHandlerInterceptorAutoConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderHandlerInterceptor()).addPathPatterns("/order/**");
    }

    @Bean
    @ConditionalOnMissingBean
    public OrderHandlerInterceptor orderHandlerInterceptor(){
        return new OrderHandlerInterceptor();
    }


    @Bean
    public FilterRegistrationBean servletRegistrationBean() {
        OrderFilter orderFilter = new OrderFilter();
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setFilter(orderFilter);
        bean.setName("orderFilter");
        bean.addUrlPatterns("/order/*");
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return bean;
    }
}
