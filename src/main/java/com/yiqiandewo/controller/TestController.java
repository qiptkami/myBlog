package com.yiqiandewo.controller;

import com.yiqiandewo.pojo.Test;
import com.yiqiandewo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public List<Test> queryAll() {
        return testService.queryAll();
    }
}
