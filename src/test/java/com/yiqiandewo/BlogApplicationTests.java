package com.yiqiandewo;

import com.yiqiandewo.mapper.TypeMapper;
import com.yiqiandewo.pojo.Blog;
import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    TypeMapper typeMapper;

    @Test
    void test() {

        String key = "blog:page";
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisUtils.zRevRangeWithScores(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 10);
        for (ZSetOperations.TypedTuple<Object> typedTuple : typedTuples) {
            System.out.println(typedTuple.getScore());
            System.out.println(typedTuple.getValue());
        }

    }
}
