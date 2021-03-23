package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {
    void addType(Type type);

    Type queryById(Long id);

    List<Type> queryAll();

    void updateType(Type type);

    void deleteType(Long id);

    Type queryByName(String name);
}
