package com.yiqiandewo.controller;

import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @GetMapping({"/", "/index"})
    public String indexPage(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                            @RequestParam(name = "size", required = true, defaultValue = "6") Integer size,
                            Model model) {
        model.addAttribute("total", blogService.getTotal());
        model.addAttribute("pageInfo", blogService.queryAll(page, size));
        model.addAttribute("recommendBlog", blogService.queryByUpdateTime(6));
        List<Type> types = typeService.queryAllBlog(6);
        model.addAttribute("types", types);
        return "index";
    }
}
