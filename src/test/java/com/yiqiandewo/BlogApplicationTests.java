package com.yiqiandewo;

import com.yiqiandewo.mapper.BlogMapper;
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
    BlogMapper blogMapper;

    @Test
    void test() {
        Blog b = new Blog();
    }
}
