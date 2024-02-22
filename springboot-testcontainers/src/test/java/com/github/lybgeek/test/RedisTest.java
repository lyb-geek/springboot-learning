package com.github.lybgeek.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;

@Testcontainers
public class RedisTest {

    @Container
    private static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6.2.6"))
            .withExposedPorts(6379);


    private Jedis jedis;

    @BeforeEach
    public void setUp() {

        int port = redis.getMappedPort(6379);
        jedis = new Jedis(redis.getHost(), port);
    }

    @AfterEach
    public void tearDown() {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Test
    public void testRedisConnectionAndSetAndGet() {
        // 测试连接和简单存取
        String key = "testKey";
        String value = "testValue";

        jedis.set(key, value);
        String result = jedis.get(key);

        assert result.equals(value);
    }
}