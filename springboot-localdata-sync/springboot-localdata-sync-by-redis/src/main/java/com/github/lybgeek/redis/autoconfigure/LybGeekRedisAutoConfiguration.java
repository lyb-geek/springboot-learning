package com.github.lybgeek.redis.autoconfigure;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.lybgeek.common.constant.DataSyncConstant;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import com.github.lybgeek.redis.listener.RedisMessageListener;
import com.github.lybgeek.redis.trigger.RedisDataSyncTrigger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;


@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class LybGeekRedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DataSyncTriggerProperty.PREFIX,name = "plugin",havingValue = DataSyncConstant.TRIGGER_TYPE_REDIS)
    public BaseDataSyncTrigger redisDataSyncTrigger(RedisTemplate redisTemplate, DataSyncTriggerProperty dataSyncTriggerProperty){
        return new RedisDataSyncTrigger(redisTemplate,dataSyncTriggerProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(name = "redisDataSyncTrigger")
    public RedisMessageListener redisMessageListener(BaseDataSyncTrigger redisDataSyncTrigger,RedisTemplate redisTemplate){
        return new RedisMessageListener(redisDataSyncTrigger,redisTemplate);
    }




}
