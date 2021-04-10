package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    //查询给定id的blog下所有的没有父comment的comment
    List<Comment> selectListByBlog(Long blogId);

    //查询出给定id的comment的子评论
    List<Comment> selectListReply(Long id);

    //通过id查询
    Comment selectOne(Long id);

    void insert(Comment comment);

}
