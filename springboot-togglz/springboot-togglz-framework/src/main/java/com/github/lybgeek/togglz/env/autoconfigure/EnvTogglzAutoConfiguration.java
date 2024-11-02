package com.github.lybgeek.togglz.env.autoconfigure;

import com.github.lybgeek.togglz.env.aop.advice.EnvTogglzMethodInterceptor;
import com.github.lybgeek.togglz.env.aop.advisor.EnvTogglzAdvisor;
import com.github.lybgeek.togglz.env.bytebuddy.processor.EnvTogglzProxyBeanPostProcessor;
import com.github.lybgeek.togglz.env.config.EnvTogglzConfig;
import com.github.lybgeek.togglz.env.exception.EnvTogglzExceptionHandler;
import com.github.lybgeek.togglz.env.factory.StateRepositoryFactory;
import com.github.lybgeek.togglz.env.factory.support.FileStateRepositoryFactory;
import com.github.lybgeek.togglz.env.factory.support.InMemoryStateRepositoryFactory;
import com.github.lybgeek.togglz.env.factory.support.JdbcStateRepositoryFactory;
import com.github.lybgeek.togglz.env.properties.EnvTogglzProperties;
import org.springframework.aop.Advisor;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;

import javax.sql.DataSource;
import java.util.List;

import static com.github.lybgeek.togglz.env.constant.TogglzConstant.*;

@Configuration
@EnableConfigurationProperties(EnvTogglzProperties.class)
public class EnvTogglzAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean
    public InMemoryStateRepositoryFactory inMemoryStateRepositoryFactory(){
        return new InMemoryStateRepositoryFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public FileStateRepositoryFactory fileStateRepositoryFactory(EnvTogglzProperties envTogglzProperties){
        return new FileStateRepositoryFactory(envTogglzProperties.getFileStoreLocation());
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcStateRepositoryFactory jdbcStateRepositoryFactory(ObjectProvider<DataSource> dataSource,EnvTogglzProperties envTogglzProperties){
        return new JdbcStateRepositoryFactory(dataSource,envTogglzProperties.getTogglzTableName());
    }


    @Bean
    @ConditionalOnMissingBean
    public EnvTogglzConfig envTogglzConfig(ObjectProvider<List<StateRepositoryFactory>> listObjectProvider,EnvTogglzProperties envTogglzProperties){
        return new EnvTogglzConfig(listObjectProvider,envTogglzProperties);

    }


    /**
     * @see <a href="https://www.togglz.org/documentation/advanced-config">...</a>
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FeatureManager featureManager(EnvTogglzConfig envTogglzConfig){
        return new FeatureManagerBuilder()
                .featureEnum(envTogglzConfig.getFeatureClass())
                .stateRepository(envTogglzConfig.getStateRepository())
                .userProvider(envTogglzConfig.getUserProvider())
                .build();
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(Advisor.class)
    @ConditionalOnProperty(prefix = EnvTogglzProperties.PREFIX,name = PROXY_TYPE_KEY,havingValue = PROXY_TYPE_AOP)
    public PointcutAdvisor envTogglzAdvisor(Environment environment){
       EnvTogglzAdvisor envTogglzAdvisor = new EnvTogglzAdvisor();
       envTogglzAdvisor.setAdvice(new EnvTogglzMethodInterceptor(environment));
       return envTogglzAdvisor;
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = EnvTogglzProperties.PREFIX,name = PROXY_TYPE_KEY,havingValue = PROXY_TYPE_BYTEBUDDY,matchIfMissing = true)
    public EnvTogglzProxyBeanPostProcessor envTogglzProxyBeanPostProcessor(){
        return new EnvTogglzProxyBeanPostProcessor();
    }


    @Bean
    @ConditionalOnMissingBean
    public EnvTogglzExceptionHandler envTogglzExceptionHandler(){
        return new EnvTogglzExceptionHandler();
    }


}
