package com.itcast.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService<K, V> {
    @Resource
    RedisTemplate<K, V> redisTemplate;

    public void setValue(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public V getValue(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setHashValue(K key, K hashKey, V value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public V getHashValue(K key, K hashKey) {
        return (V) redisTemplate.opsForHash().get(key, hashKey);
    }
}
