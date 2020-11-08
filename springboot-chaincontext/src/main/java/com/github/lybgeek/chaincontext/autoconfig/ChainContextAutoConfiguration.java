package com.github.lybgeek.chaincontext.autoconfig;

import com.github.lybgeek.chaincontext.ChainContextProperties;
import com.github.lybgeek.chaincontext.feign.FeignConfiguration;
import com.github.lybgeek.chaincontext.hystrix.HystrixChainContextConfiguration;
import com.github.lybgeek.chaincontext.mvc.ChainContextWebMvcConfigure;
import com.github.lybgeek.chaincontext.mvc.ChainContexthandlerInterceptor;
import com.github.lybgeek.chaincontext.parser.DefaultRequestKeyParser;
import com.github.lybgeek.chaincontext.parser.IRequestKeyParser;
import com.github.lybgeek.chaincontext.rest.ChainContextBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 *
 * @author linyb
 *
 */
@Configuration
@Import({FeignConfiguration.class, HystrixChainContextConfiguration.class})
@EnableConfigurationProperties(ChainContextProperties.class)
public class ChainContextAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    private ChainContextProperties chainContextProperties;

    @ConditionalOnMissingBean
    @Bean
    public IRequestKeyParser requestKeyParser() {
        return new DefaultRequestKeyParser();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(IRequestKeyParser requestKeyParser) {
        ChainContexthandlerInterceptor interceptor = new ChainContexthandlerInterceptor(chainContextProperties, requestKeyParser);
        return new ChainContextWebMvcConfigure(interceptor);
    }

    @Bean
    public ChainContextBeanPostProcessor chainContextBeanPostProcessor() {
        return new ChainContextBeanPostProcessor();
    }
    
}
