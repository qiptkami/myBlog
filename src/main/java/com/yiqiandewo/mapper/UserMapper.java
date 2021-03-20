package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findUserByUsernameAndPassword(String username, String password);

}
