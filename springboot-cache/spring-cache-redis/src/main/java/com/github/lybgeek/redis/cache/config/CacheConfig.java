package com.github.lybgeek.redis.cache.config;

import cn.hutool.extra.spring.SpringUtil;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lybgeek.redis.cache.CustomizedRedisCacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * @description: 缓存自动装配
 **/
@Configuration
@Import(SpringUtil.class)
@ComponentScan(basePackages = "com.github.lybgeek.redis")
@EnableCaching
public class CacheConfig {

    public static final String REDISTEMPLATE_BEAN_NAME = "cacheRedisTemplate";


    @Bean(REDISTEMPLATE_BEAN_NAME)
    public RedisTemplate<String, Object> cacheRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisTemplate<Object, Object> cacheRedisTemplate, ObjectProvider<List<CacheItemConfig>> cacheItemConfigs) {

        CustomizedRedisCacheManager cacheManager = new CustomizedRedisCacheManager(connectionFactory,cacheRedisTemplate,cacheItemConfigs.getIfAvailable());

        return cacheManager;
    }

}
