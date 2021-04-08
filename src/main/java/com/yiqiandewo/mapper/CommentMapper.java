package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void addComment(Comment comment);

    List<Comment> queryAllByBlogId(Long blogId);

    Comment queryByParentId(Long parentCommentId);

    List<Comment> queryAllReplyById(Long id);

    Comment queryById(Long id);
}
