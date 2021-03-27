package com.yiqiandewo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiqiandewo.mapper.BlogMapper;
import com.yiqiandewo.pojo.Blog;
import com.yiqiandewo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Blog addBlog(Blog blog) {
        Blog b = blogMapper.queryByTitle(blog.getTitle());
        if (b != null) {
            return null;
        }
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogMapper.addBlog(blog);
        return blog;
    }

    @Override
    public Blog queryById(Long id) {
        return blogMapper.queryById(id);
    }

    @Override
    public PageInfo<Blog> queryAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogMapper.queryAll();
        PageInfo<Blog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogMapper.queryById(id);
        if (b == null) {
            return null;
        }
        //标题 内容 分类 首图   recommend 推荐   shareStatement转载 appreciation赞赏   commentAble评论 updateTime type
        blog.setUpdateTime(new Date());
        blogMapper.updateBlog(blog);
        return blog;
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public Blog queryByTitle(String title) {
        return blogMapper.queryByTitle(title);
    }

    @Override
    public PageInfo<Blog> queryConditional(int page, int size, String title, Long typeId, boolean recommend) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogMapper.queryConditional(title, typeId, recommend);
        PageInfo<Blog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
