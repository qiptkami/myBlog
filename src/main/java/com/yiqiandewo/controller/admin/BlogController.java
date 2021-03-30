package com.yiqiandewo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Blog;
import com.yiqiandewo.pojo.User;
import com.yiqiandewo.service.BlogService;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", typeService.queryAll());
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
    public String input(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.queryAll());
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}")
    public String updatePage(@PathVariable Long id, Model model) {
        Blog blog = blogService.queryById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("types", typeService.queryAll());
        return "admin/blogs-input";
    }

    @PostMapping("/blogs/input")
    public String addBlog(@Valid Blog blog, BindingResult result, RedirectAttributes attributes, HttpSession session, Model model) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.queryById(blog.getType().getId()));
        Blog b = null;
        if (blog.getId() != null) {  //修改的时候blog id 不为null
            b = blogService.updateBlog(blog.getId(), blog);
        } else {  //新增 id为null
            if (blogService.queryByTitle(blog.getTitle()) != null) {
                result.rejectValue("title", "titleError", "该标题已存在");
            }
            if (result.hasErrors()) {
                System.out.println(result.getAllErrors().toString());
                model.addAttribute("types", typeService.queryAll());
                return "admin/blogs-input";
            }
            b = blogService.addBlog(blog);
        }

        if (b == null) {
            attributes.addFlashAttribute("errMsg", "操作失败");
        } else {
            attributes.addFlashAttribute("successMsg", "操作成功");
        }

        return "redirect:/admin/blogs";
    }

    @DeleteMapping("/blogs/{id}")
    public String delBlog1(@PathVariable Long id) {
        System.out.println(id);
        blogService.deleteBlog(id);
        return "redirect:/admin/blogs";
    }

}
