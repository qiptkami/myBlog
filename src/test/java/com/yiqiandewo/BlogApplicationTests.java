package com.yiqiandewo;

import com.yiqiandewo.mapper.BlogMapper;
import com.yiqiandewo.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void test() {

        List<Blog> java = blogMapper.query("Java");
        System.out.println(java.toString());
    }
}
