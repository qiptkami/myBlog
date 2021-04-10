package com.yiqiandewo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiqiandewo.mapper.TypeMapper;
import com.yiqiandewo.pojo.Blog;
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
    public Type selectOne(Long id) {
        return typeMapper.selectOneById(id);
    }

    @Override
    public Type selectOne(String name) {
        return typeMapper.selectOneByName(name);
    }

    /**
     * 仅仅查询所有的type
     * @return
     */
    @Override
    public List<Type> selectList() {
        return typeMapper.selectList();
    }

    @Override
    public List<Type> selectList(int size) {
        List<Type> types = typeMapper.selectListAndBlog();  //拿到types中 blog数量最多的size个type
        types.sort(new Comparator<Type>() {
            @Override
            public int compare(Type t1, Type t2) {
                return t2.getBlogs().size() - t1.getBlogs().size();
            }
        });

        if (size >= types.size()) {
            return types;
        }
        return types.subList(0, size);
    }

    @Override
    public PageInfo<Type> selectList(int page, int size) {
        PageHelper.startPage(page, size);
        List<Type> types = typeMapper.selectList();
        return new PageInfo<>(types);
    }


    @Override
    public Type insert(Type type) {
        //首先判断数据库中有无这个数据
        Type t = typeMapper.selectOneByName(type.getName());
        if (t != null) {
            return null;
        }
        typeMapper.insert(type);
        return type;
    }

    @Override
    public Type update(Long id, Type type) {
        Type t = typeMapper.selectOneById(id);
        if (t == null) {
            return null;
        }
        typeMapper.update(type);
        return type;
    }

    @Override
    public void delete(Long id) {
        typeMapper.delete(id);
    }


}
