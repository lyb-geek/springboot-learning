package com.github.lybgeek.apollo.autoconfigure;


import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.github.lybgeek.apollo.property.ApolloOpenApiProperties;
import com.github.lybgeek.apollo.property.AppInfoProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApolloOpenApiProperties.class, AppInfoProperties.class})
public class ApolloOpenApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApolloOpenApiClient apolloOpenApiClient(ApolloOpenApiProperties apolloOpenApiProperties){
        ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
                .withPortalUrl(apolloOpenApiProperties.getPortalUrl())
                .withToken(apolloOpenApiProperties.getToken())
                .build();
        return client;
    }
}
