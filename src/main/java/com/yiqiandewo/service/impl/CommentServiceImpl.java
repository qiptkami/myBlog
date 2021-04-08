package com.yiqiandewo.service.impl;

import com.yiqiandewo.mapper.CommentMapper;
import com.yiqiandewo.pojo.Comment;
import com.yiqiandewo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            Comment parentComment = commentMapper.queryByParentId(parentCommentId);
            System.out.println(parentCommentId);
            comment.setParentComment(parentComment);
        }
        commentMapper.addComment(comment);
    }

    private List<Comment> tempList = new ArrayList<>();

    @Override
    public List<Comment> queryAllByBlogId(Long blogId) {
        //首先查询出所有没有parent的comment
        List<Comment> list = commentMapper.queryAllByBlogId(blogId);

        for (Comment comment : list) {
            List<Comment> replyList = commentMapper.queryAllReplyById(comment.getId());
            for (Comment reply : replyList) {
                tempList.add(reply);
                //子回复也可能有子回复
                recReply(reply);
            }
            comment.setReplyComment(tempList);

            for (Comment comment1 : tempList) {
                comment1.getParentComment().setNickname(commentMapper.queryById(comment1.getParentComment().getId()).getNickname());
            }

            tempList = new ArrayList<>();
        }

        return list;
    }

    private void recReply(Comment comment) {
        List<Comment> replyList = commentMapper.queryAllReplyById(comment.getId());
        if (replyList.size() > 0) {
            for (Comment reply : replyList) {
                tempList.add(reply);  //如果不用全局变量  这个reply将无法返回
                if (commentMapper.queryAllReplyById(reply.getId()).size() > 0) {
                    //子回复也可能有子回复
                    recReply(reply);
                }
            }
        }
    }

    @Override
    public Comment queryByParentId(Long id) {
        return commentMapper.queryByParentId(id);
    }
}
