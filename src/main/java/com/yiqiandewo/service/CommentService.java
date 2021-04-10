package com.yiqiandewo.service;

import com.yiqiandewo.pojo.Comment;

import java.util.List;

public interface CommentService {

    void insert(Comment comment);

    List<Comment> selectList(Long blogId);

}
