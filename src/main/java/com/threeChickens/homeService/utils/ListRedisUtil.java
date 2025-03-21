package com.threeChickens.homeService.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ListRedisUtil<T> {
    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    public int size(String key){
        return Objects.requireNonNull(redisTemplate.opsForList().size(key)).intValue();
    }

    public void addToList(String key, T value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // 🔹 Lấy toàn bộ Set từ Redis
    public List<T> getList(String key) {
        return redisTemplate.opsForList().range(key, 0,-1);
    }

    public void updateListById(String key, int index, T value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    public T getValueById(String key, int index) {
        return redisTemplate.opsForList().index(key, index);
    }
}
