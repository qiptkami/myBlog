package com.yiqiandewo.service.impl;

import com.yiqiandewo.mapper.TestMapper;
import com.yiqiandewo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> queryAll() {
        return testMapper.queryAll();
    }
}
