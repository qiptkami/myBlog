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
        List<Blog> list = blogMapper.queryConditional("", 4L, true);

        System.out.println("==================================");
        System.out.println("================");
        System.out.println("================");
        for (Blog blog : list) {
            System.out.println(blog);
            System.out.println(blog.getType());
        }
        System.out.println("================");
        System.out.println("================");
        System.out.println("===================================");
    }
}
