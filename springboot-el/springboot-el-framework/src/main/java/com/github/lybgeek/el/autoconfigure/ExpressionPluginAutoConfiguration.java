package com.github.lybgeek.el.autoconfigure;

import com.github.lybgeek.el.plugin.ExpressionPlugin;
import com.github.lybgeek.el.plugin.support.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;


@Configuration
@Slf4j
public class ExpressionPluginAutoConfiguration {


    @Bean
    @ConditionalOnClass(name = "com.googlecode.aviator.AviatorEvaluator")
    @ConditionalOnMissingBean
    public AviatorExpressionPlugin aviatorExpressionPlugin(){
        log.info("Loaded ExpressionPlugin [Aviator]");
        return new AviatorExpressionPlugin();
    }

    @Bean
    @ConditionalOnClass(name = "cn.hutool.extra.expression.ExpressionUtil")
    @ConditionalOnMissingBean
    public HutoolExpressionPlugin hutoolExpressionPlugin(){
        log.info("Loaded ExpressionPlugin [Hutool]");
        return new HutoolExpressionPlugin();
    }


    @Bean
    @ConditionalOnClass(name = "org.mvel2.MVEL")
    @ConditionalOnMissingBean
    public MvelExpressionPlugin mvelExpressionPlugin(){
        log.info("Loaded ExpressionPlugin [Mvel]");
        return new MvelExpressionPlugin();
    }

    @Bean
    @ConditionalOnClass(name = "ognl.Ognl")
    @ConditionalOnMissingBean
    public OgnlExpressionPlugin ognlExpressionPlugin(){
        log.info("Loaded ExpressionPlugin [Ognl]");
        return new OgnlExpressionPlugin();
    }


    @Bean
    @ConditionalOnClass(name = "org.springframework.expression.ExpressionParser")
    @ConditionalOnMissingBean
    public SpringExpressionPlugin springExpressionPlugin(){
        log.info("Loaded ExpressionPlugin [Spel]");
        return new SpringExpressionPlugin();
    }


    @Bean
    @Primary
    public ExpressionPlugin expressionPlugin(ObjectProvider<List<ExpressionPlugin>> provider){
        return new CompoisteExpressionPlugin(provider);
    }




}
