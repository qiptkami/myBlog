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

    private List<Comment> tempList = new ArrayList<>();

    /**
     * 查询给定id的blog下所有的没有父comment的comment
     * 并且将他们的子评论封装进去
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> selectList(Long blogId) {
        //首先查询出所有没有parent的comment
        List<Comment> list = commentMapper.selectListByBlog(blogId);

        for (Comment comment : list) { //遍历父评论
            //查询出子评论 即所有parentId = comment.id 的评论
            List<Comment> replyList = commentMapper.selectListReply(comment.getId());
            for (Comment reply : replyList) { //遍历一级子评论
                tempList.add(reply);
                //子评论也可能有子评论
                recReply(reply);//二级...三级...子评论
            }
            //将一级子评论封装进父评论中
            comment.setReplyComment(tempList);

            //前端显示的是 @父评论NickName，所以要查出父评论的NickName
            for (Comment c : tempList) {
                c.getParentComment().setNickname(commentMapper.selectOne(c.getParentComment().getId()).getNickname());
            }

            tempList = new ArrayList<>();
        }
        
        return list;
    }

    private void recReply(Comment comment) {
        List<Comment> replyList = commentMapper.selectListReply(comment.getId());
        if (replyList.size() > 0) {
            for (Comment reply : replyList) {
                tempList.add(reply);  //如果不用全局变量  这个reply将无法返回
                if (commentMapper.selectListReply(reply.getId()).size() > 0) {
                    //子评论也可能有子评论
                    recReply(reply);
                }
            }
        }
    }

    @Override
    public void insert(Comment comment) {
        comment.setCreateTime(new Date());
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId == -1) {
            comment.setParentComment(null);
        } else {
            //查出父评论 封装
            Comment parentComment = commentMapper.selectOne(parentCommentId);
            comment.setParentComment(parentComment);
        }
        commentMapper.insert(comment);
    }


}
