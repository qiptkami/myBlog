package com.yiqiandewo.controller;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Blog;
import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.service.TypeService;
import com.yiqiandewo.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
        PageInfo<Blog> pageInfo = blogService.selectList(page, size, true);
        model.addAttribute("pageInfo", pageInfo); //只查询发布的  不查询保存的
        model.addAttribute("recommendBlog", blogService.selectList(6));
        Map<Type, Integer> map = typeService.selectList(6);
        model.addAttribute("typeMap", map);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                         @RequestParam(name = "size", required = true, defaultValue = "6") Integer size,
                         String query,
                         Model model) {
        model.addAttribute("pageInfo", blogService.selectList(page, size, query));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = blogService.selectOne(id);
        blogService.incrView(id, blog);
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        model.addAttribute("blog", blog);
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String footer(Model model) {
        List<Blog> list = blogService.selectList(3);
        model.addAttribute("newBlogs", list);
        return "commons/bar :: newBlogList";
    }
}
