package com.yiqiandewo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void test() {
        redisTemplate.opsForValue().set("k1", "v1");

        System.out.println(redisTemplate.opsForValue().get("k1"));
    }
}
