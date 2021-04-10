package com.yiqiandewo.controller;
import com.yiqiandewo.pojo.Comment;
import com.yiqiandewo.pojo.User;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.service.CommentService;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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
        List<Comment> list = commentService.selectList(blogId);
        model.addAttribute("comments", list);  //根据blogId查询出所有没有parentComment的Comment
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String insert(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        User user = (User) session.getAttribute("user");
        comment.setBlog(blogService.selectOne(blogId));
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setNickname(user.getUsername());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        commentService.insert(comment);
        return "redirect:/comments/" + blogId;
    }
}
