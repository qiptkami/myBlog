package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {
    //@Select("select * from test")
    List<Test> queryAll();
}
