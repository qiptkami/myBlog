package com.yiqiandewo;

import com.yiqiandewo.pojo.Comment;
import com.yiqiandewo.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private CommentService commentService;

    @Test
    void test() {
        List<Comment> list = commentService.queryAllByBlogId(31L);

        for (Comment comment : list) {
            System.out.println(comment);
            List<Comment> replyComment = comment.getReplyComment();
            for (Comment c : replyComment) {
                System.out.println("=========================reply=========================");
                System.out.println(c);
                System.out.println(c.getParentComment());
                System.out.println("=========================reply=========================");
            }
        }
    }
}
