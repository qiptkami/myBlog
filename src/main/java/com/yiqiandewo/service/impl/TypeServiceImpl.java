package com.yiqiandewo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiqiandewo.mapper.TypeMapper;
import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public void addType(Type type) {
        typeMapper.addType(type);
    }

    @Override
    public Type queryById(Long id) {
        return typeMapper.queryById(id);
    }

    @Override
    public PageInfo<Type> queryAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<Type> types = typeMapper.queryAll();
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        return pageInfo;
    }

    @Override
    public void updateType(Type type) {
        typeMapper.updateType(type);
    }

    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }
}
