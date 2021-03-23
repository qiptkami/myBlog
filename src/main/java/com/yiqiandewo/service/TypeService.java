package com.yiqiandewo.service;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Type;

public interface TypeService {
    Type addType(Type type);

    Type queryById(Long id);

    //分页查询
    PageInfo<Type> queryAll(int start, int size);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

    Type queryByName(String name);
}
