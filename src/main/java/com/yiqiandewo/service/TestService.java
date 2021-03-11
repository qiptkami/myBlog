package com.yiqiandewo.service;

import com.yiqiandewo.pojo.Test;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TestService {
     List<Test> queryAll();
}
