package com.journalApp.jounalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journalApp.jounalApp.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T getRes(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            assert o != null;
            return mapper.readValue(o.toString(),entityClass);
        }catch (Exception e){
            log.error("Exception ",e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,jsValue,ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception ",e);
        }
    }
}
