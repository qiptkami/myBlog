package com.yiqiandewo.service;

import com.yiqiandewo.pojo.User;

public interface UserService {

    User check(String username, String password);
}
