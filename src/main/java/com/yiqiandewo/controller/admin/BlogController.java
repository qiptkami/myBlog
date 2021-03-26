package com.yiqiandewo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Blog;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String list(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                       @RequestParam(name = "size", required = true, defaultValue = "3") Integer size,
                       Model model) {
        PageInfo<Blog> pageInfo = blogService.queryAll(page, size);
        List<Blog> list = pageInfo.getList();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", typeService.queryAll());
        for (Blog blog : list) {
            System.out.println(blog);
        }
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                         @RequestParam(name = "size", required = true, defaultValue = "3") Integer size,
                         Model model, String title, Long typeId, boolean recommend) {
        PageInfo<Blog> pageInfo = blogService.queryConditional(page, size, title, typeId, recommend);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input() {
        return "admin/blogs-input";
    }

}
