package com.yiqiandewo;

import com.yiqiandewo.mapper.TypeMapper;
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
    TypeMapper typeMapper;

    @Test
    void test() {

        String key = "type_blogs";
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisUtils.zRevRangeWithScores(key, 0, -1);

        for (ZSetOperations.TypedTuple<Object> typedTuple : typedTuples) {
            Integer score = typedTuple.getScore().intValue();
            Object value = typedTuple.getValue();
            System.out.println(value);
            System.out.println(score);

            System.out.println(redisUtils.get("type_id:" + value));
        }

    }
}
