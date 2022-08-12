package com.github.lybgeek.aspect.autoconfigure;


import com.github.lybgeek.aspect.MemberAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public MemberAspect memberAspect(){
       return new MemberAspect();
    }

}
