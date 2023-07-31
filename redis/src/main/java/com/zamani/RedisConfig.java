package com.zamani;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory c = new LettuceConnectionFactory();
        c.setDatabase(0);
        c.setPort(6379);
        c.setHostName("192.168.112.83");
        return c;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
