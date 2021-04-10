package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {

    Blog selectOneById(Long id);

    Blog selectOneByTitle(String title);

    //查询所有blog
    List<Blog> selectList();

    //按照updateTime排序 查询size个
    List<Blog> selectListByUpdateTime(int size);

    //查询所有published是true
    List<Blog> selectListPublished();

    //首页查询
    List<Blog> selectListConditional(String query);

    //后端条件查询
    List<Blog> selectListMultipleConditional(String title, Long typeId, boolean recommend);

    void updateViews(Long id);

    void insert(Blog blog);

    void update(Blog blog);

    void delete(Long id);
}
