package com.yiqiandewo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Type;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String typeIndex(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                                @RequestParam(name = "size", required = true, defaultValue = "5") Integer size,
                                 Model model) {
        PageInfo<Type> pageInfo = typeService.queryAll(page, size);
        model.addAttribute("pageInfo", pageInfo);
        List<Type> list = pageInfo.getList();
        for (Type type : list) {
            System.out.println(type);
        }
        return "admin/types";
    }

    @PostMapping("/types")
    public String addType(Type type) {

        return "redirect:/admin/types";  //返回到 /admin/type 请求 再去查询
    }
}
