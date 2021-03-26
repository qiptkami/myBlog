package com.yiqiandewo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiqiandewo.mapper.BlogMapper;
import com.yiqiandewo.pojo.Blog;
import com.yiqiandewo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Blog addBlog(Blog blog) {
        return null;
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
        return null;
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public Blog queryByName(String name) {
        return null;
    }

    @Override
    public PageInfo<Blog> queryConditional(int page, int size, String title, Long typeId, boolean recommend) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogMapper.queryConditional(title, typeId, recommend);
        PageInfo<Blog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
