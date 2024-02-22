package com.github.lybgeek.test;

import com.github.lybgeek.testcontainers.TestcontainersApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Objects;

@SpringBootTest(classes = TestcontainersApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers(disabledWithoutDocker = true)
public class RedisContainerByDynamicPropertySourceTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Container
    private static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6.2.6"))
            .withExposedPorts(6379);

//    @BeforeEach
//    public void setUp() {
//
//        System.setProperty("spring.redis.host", redis.getHost());
//        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
//    }

    /**
     * Spring TEST 5.2.5才引入DynamicPropertySource
     * @param registry
     */
    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379)
                .toString());
    }

    @Test
    public void testRedisConnectionAndSetAndGet() {
        // 测试连接和简单存取
        String key = "testKey";
        String value = "testValue";
        redisTemplate.opsForValue().set(key, value);
        String result = redisTemplate.opsForValue().get(key);

        assert Objects.equals(result, value);
    }
}
