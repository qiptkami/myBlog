package com.yiqiandewo.controller;
import com.yiqiandewo.pojo.Comment;
import com.yiqiandewo.pojo.User;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.service.CommentService;
import com.yiqiandewo.util.CookieUtils;
import com.yiqiandewo.util.JWTUtils;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String insert(Comment comment, HttpServletRequest request) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.selectOne(blogId));

        try {
            /*Cookie cookie = CookieUtils.get(request, "token");
            String token = cookie.getValue();
            JWTUtils.verifyToken(token);//验证token令牌
            //拿到token payload中的username
            String username = JWTUtils.parserToken(token, "username");
            String avatar = JWTUtils.parserToken(token, "avatar");
            comment.setAvatar(avatar);
            comment.setNickname(username);
            comment.setAdminComment(true);*/
        } catch (Exception e) {
            System.out.println("签名无效！！！");
        }

        //验证失败 说明是游客
        comment.setAvatar(avatar);
        comment.setAdminComment(false);
        commentService.insert(comment);

        return "redirect:/comments/" + blogId;
    }
}
