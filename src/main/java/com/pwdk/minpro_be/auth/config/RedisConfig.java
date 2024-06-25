package com.pwdk.minpro_be.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.context.annotation.Bean;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<? ,?> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<? ,?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
