package com.github.lybgeek.desensitization.autoconfigure;


import com.github.lybgeek.desensitization.autoconfigure.condition.ConditionalOnDesensitizedFile;
import com.github.lybgeek.desensitization.autoconfigure.condition.ConditionalOnMissingDesensitizedFile;
import com.github.lybgeek.desensitization.mybatis.config.DesensitizedInterceptorConfig;
import com.github.lybgeek.desensitization.property.DesensitizedProperties;
import com.github.lybgeek.desensitization.service.DesensitizedService;
import com.github.lybgeek.desensitization.service.impl.DefaultDesensitizedService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.github.lybgeek.desensitization.property.DesensitizedProperties.*;

@Configuration
@EnableConfigurationProperties(DesensitizedProperties.class)
@Import({DesensitizedInterceptorConfig.class})
@ConditionalOnProperty(prefix = PREFIX,name = ENABLED,havingValue = "true",matchIfMissing = true)
public class DesensitizedAutoConfiguration {


    @Bean
    @ConditionalOnMissingDesensitizedFile
    public DesensitizedService desensitizedService(){
        return new DefaultDesensitizedService();
    }

    @Bean
    @ConditionalOnDesensitizedFile
    public DesensitizedService desensitizedServiceWithFile(DesensitizedProperties properties){
        return new DefaultDesensitizedService(properties);
    }


}
