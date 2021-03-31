package com.yiqiandewo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Deque;
import java.util.LinkedList;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void test() {
        Deque<Integer> stack = new LinkedList<>();
        stack.peekLast();
    }
}
