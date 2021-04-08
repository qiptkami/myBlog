package com.yiqiandewo.service;

import com.yiqiandewo.pojo.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> queryAllByBlogId(Long blogId);

    Comment queryById(Long id);
}
