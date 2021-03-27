package com.yiqiandewo.service;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Blog;

public interface BlogService {
    Blog addBlog(Blog blog);

    Blog queryById(Long id);

    //分页查询
    PageInfo<Blog> queryAll(int page, int size);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    PageInfo<Blog> queryConditional(int page, int size, String title, Long typeId, boolean recommend);

    Blog queryByTitle(String title);
}
