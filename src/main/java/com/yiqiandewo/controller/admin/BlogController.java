package com.yiqiandewo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @GetMapping("/blogs")
    public String list() {
        return "admin/blogs";
    }

    @GetMapping("/blogs/input")
    public String input() {
        return "admin/blogs-input";
    }
}
