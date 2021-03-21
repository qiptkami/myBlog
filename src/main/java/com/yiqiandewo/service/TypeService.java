package com.yiqiandewo.service;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Type;

public interface TypeService {
    void addType(Type type);

    Type queryById(Long id);

    //分页查询
    PageInfo<Type> queryAll(int start, int size);

    void updateType(Type type);

    void deleteType(Long id);
}
