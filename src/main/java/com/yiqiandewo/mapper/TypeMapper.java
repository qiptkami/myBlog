package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {
    void insert(Type type);

    void update(Type type);

    void delete(Long id);

    Type selectOneById(Long id);

    Type selectOneByName(String name);

    List<Type> selectList();

    List<Type> selectListAndBlog();
}
