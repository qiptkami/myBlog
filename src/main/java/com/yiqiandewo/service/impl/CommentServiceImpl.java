package com.yiqiandewo.service.impl;

import com.yiqiandewo.mapper.CommentMapper;
import com.yiqiandewo.pojo.Comment;
import com.yiqiandewo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(new Date());
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId == -1) {
            comment.setParentComment(null);
        } else {
            Comment parentComment = commentMapper.queryById(parentCommentId);
            System.out.println(parentCommentId);
            comment.setParentComment(parentComment);
        }
        commentMapper.addComment(comment);
    }

    @Override
    public List<Comment> queryAllByBlogId(Long blogId) {
        return commentMapper.queryAllByBlogId(blogId);
    }

    @Override
    public Comment queryById(Long id) {
        return commentMapper.queryById(id);
    }
}
