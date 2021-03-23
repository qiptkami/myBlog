package com.yiqiandewo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}")
    public String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.queryById(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String addType(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (typeService.queryByName(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.addType(type);
        if (t == null) {
            attributes.addFlashAttribute("errMsg","新增失败");
        } else {
            attributes.addFlashAttribute("successMsg","新增成功");
        }
        return "redirect:/admin/types";  //返回到 /admin/type 请求 再去查询
    }

    @PutMapping("/types/{id}")
    public String updateType(@PathVariable Long id, @Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (typeService.queryByName(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("errMsg","更新失败");
        } else {
            attributes.addFlashAttribute("successMsg","更新成功");
        }
        return "redirect:/admin/types";  //返回到 /admin/type 请求 再去查询
    }

    @DeleteMapping("/types/{id}")
    public String delType(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("successMsg", "删除成功");
        return "redirect:/admin/types";
    }
}
