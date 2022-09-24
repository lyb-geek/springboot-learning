package com.github.lybgeek.redis.autoconfigure;

import com.github.lybgeek.redis.LettuceRedisTemplate;
import com.github.lybgeek.redis.properties.RedisProperties;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class LettuceRedisTemplateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisClient redisClient(RedisProperties redisProperties){
        RedisURI redisUri = RedisURI.Builder.redis(redisProperties.getHost())
                .withPort(redisProperties.getPort())
                .withPassword(redisProperties.getPassword().toCharArray())
                .withDatabase(redisProperties.getDatabase())
                .build();

        return RedisClient.create(redisUri);
    }

    @ConditionalOnMissingBean
    @Bean
    public LettuceRedisTemplate lettuceRedisTemplate(RedisClient redisClient){
        return new LettuceRedisTemplate(redisClient);
    }
}
