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
    public Blog selectOne(Long id) {
        Blog blog = blogMapper.selectOneById(id);
        blog.setViews(blog.getViews()+1);
        blogMapper.updateViews(blog.getId());
        return blog;
    }

    @Override
    public Blog selectOne(String title) {
        return blogMapper.selectOneByTitle(title);
    }

    @Override
    public List<Blog> selectList(int size) {
        return blogMapper.selectListByUpdateTime(size);
    }

    @Override
    public PageInfo<Blog> selectList(int page, int size) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogMapper.selectList();
        return new PageInfo<>(list);
    }

    public PageInfo<Blog> selectList(int page, int size, boolean published) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogMapper.selectListPublished();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Blog> selectList(int page, int size, String query) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogMapper.selectListConditional(query);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Blog> selectList(int page, int size, String title, Long typeId, boolean recommend) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogMapper.selectListMultipleConditional(title, typeId, recommend);
        return new PageInfo<>(list);
    }

    @Override
    public Blog update(Long id, Blog blog) {
        Blog b = blogMapper.selectOneById(id);
        if (b == null) {
            return null;
        }
        blog.setUpdateTime(new Date());
        blogMapper.update(blog);
        return blog;
    }

    @Override
    public Blog insert(Blog blog) {
        Blog b = blogMapper.selectOneByTitle(blog.getTitle());
        if (b != null) {
            return null;
        }
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogMapper.insert(blog);
        return blog;
    }

    @Override
    public void delete(Long id) {
        blogMapper.delete(id);
    }


}
