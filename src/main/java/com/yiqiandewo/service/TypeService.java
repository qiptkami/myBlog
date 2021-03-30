package com.yiqiandewo.service;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Type;

import java.util.List;

public interface TypeService {
    Type addType(Type type);

    Type queryById(Long id);

    //分页查询
    PageInfo<Type> queryAll(int page, int size);

    List<Type> queryAll();

    Type updateType(Long id, Type type);

    void deleteType(Long id);

    Type queryByName(String name);

    List<Type> queryAllBlog(int size);
}
