package com.yiqiandewo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    //@Select("select * from test")
    List<Test> queryAll();
}
