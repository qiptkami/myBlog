package com.yiqiandewo.controller;

import com.yiqiandewo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public List<Test> queryAll() {
        //int i = 10 / 0;
        System.out.println("index");
        return testService.queryAll();
    }

    @GetMapping("/{id}")
    public Integer que(@PathVariable Integer id) {
        System.out.println(id);
        return id;
    }
}
