package io.ssafy.p.j11a307.user.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisTest {

    private RedisTemplate<String, Object> redisTemplate;
    private LettuceConnectionFactory lettuceConnectionFactory;

    @BeforeEach
    public void setup() {
        // Redis 연결을 위한 설정
        lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setHostName("localhost");
        lettuceConnectionFactory.setPort(6379);
        lettuceConnectionFactory.afterPropertiesSet();

        // RedisTemplate 설정
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
    }

    @Test
    public void singleValueTest() {
        String key = "testKey";
        Long value = 145L;
        redisTemplate.opsForValue().set(key, value);

        Long retrievedValue = (Long) redisTemplate.opsForValue().get(key);
        Assertions.assertThat(retrievedValue).isEqualTo(value);
    }
}
