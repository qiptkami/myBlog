package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {
    void addBlog(Blog blog);

    Blog queryById(Long id);

    //分页查询
    List<Blog> queryAll();

    void updateBlog(Blog blog);

    void deleteBlog(Long id);

    List<Blog> queryConditional(String title, Long typeId, boolean recommend);

    Blog queryByTitle(String title);

    Integer total();

    List<Blog> queryByUpdateTime(int size);

    List<Blog> queryRecommend();
}
