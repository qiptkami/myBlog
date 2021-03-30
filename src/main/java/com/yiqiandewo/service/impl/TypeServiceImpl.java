package com.yiqiandewo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiqiandewo.mapper.TypeMapper;
import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type addType(Type type) {
        //首先判断数据库中有无这个数据
        Type t = typeMapper.queryByName(type.getName());
        if (t != null) {
            return null;
        }
        typeMapper.addType(type);
        return type;
    }

    @Override
    public List<Type> queryAllBlog(int size) {
        List<Type> types = typeMapper.queryAllBlog();  //拿到types中 blog数量最多的size个type
        types.sort(new Comparator<Type>() {
            @Override
            public int compare(Type t1, Type t2) {
                return t2.getBlogs().size() - t1.getBlogs().size();
            }
        });

        return types.subList(0, 6);
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
    public List<Type> queryAll() {
        return typeMapper.queryAll();
    }

    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeMapper.queryById(id);
        if (t == null) {
            return null;
        }
        typeMapper.updateType(type);
        return type;
    }

    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }

    @Override
    public Type queryByName(String name) {
        return typeMapper.queryByName(name);
    }
}
