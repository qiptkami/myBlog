package com.yiqiandewo.service;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Type;

import java.util.List;

public interface TypeService {
    Type insert(Type type);

    Type update(Long id, Type type);

    void delete(Long id);

    Type selectOne(String name);

    Type selectOne(Long id);

    List<Type> selectList();

    //查询所有type和一对多的blog
    List<Type> selectList(int size);

    //分页查询
    PageInfo<Type> selectList(int page, int size);

}
