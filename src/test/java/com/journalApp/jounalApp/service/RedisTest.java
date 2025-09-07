package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.JournalApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void testSendMail(){
        redisTemplate.opsForValue().set("email","vivek@glbitm.ac.in");
        Object email = redisTemplate.opsForValue().get("salary");
        int a = 8;
    }
}
