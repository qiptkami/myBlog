package com.yiqiandewo.controller;
import com.yiqiandewo.pojo.Comment;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String commentList(@PathVariable Long blogId, Model model) {
        List<Comment> list = commentService.queryAllByBlogId(blogId);
        model.addAttribute("comments", list);  //根据blogId查询出所有没有parentComment的Comment
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String addComment(Comment comment) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.queryById(blogId));
        comment.setAvatar(avatar);
        commentService.addComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
