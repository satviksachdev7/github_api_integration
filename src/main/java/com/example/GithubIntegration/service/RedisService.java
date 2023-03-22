package com.example.GithubIntegration.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOperations;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public <T> T getCacheValue(String key, Class<T> type) {
        return (T) valueOperations.get(key);
    }

    public void setCacheValue(String key, Object value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }
}
