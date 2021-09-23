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
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 分页查询所有types 并跳转到admin/types.html
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String indexPage(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                                @RequestParam(name = "size", required = true, defaultValue = "5") Integer size,
                                 Model model) {
        PageInfo<Type> pageInfo = typeService.selectList(page, size);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    /**
     * 跳转到更新type的页面，并且将要修改的type传过去
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.selectOne(id));
        return "admin/types-input";
    }

    /**
     * 跳转到添加type的页面
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String insertPage(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 添加type，并重定向到type首页
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String insert(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (typeService.selectOne(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.insert(type);
        if (t == null) {
            attributes.addFlashAttribute("errMsg","新增失败");
        } else {
            attributes.addFlashAttribute("successMsg","新增成功");
        }
        return "redirect:/admin/types";  //返回到 /admin/type 请求 再去查询
    }

    /**
     * 更新type，并重定向到type首页
     * @param id
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PutMapping("/types/{id}")
    public String update(@PathVariable Long id, @Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (typeService.selectOne(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.update(id, type);
        if (t == null) {
            attributes.addFlashAttribute("errMsg","更新失败");
        } else {
            attributes.addFlashAttribute("successMsg","更新成功");
        }
        return "redirect:/admin/types";  //返回到 /admin/type 请求 再去查询
    }

    /**
     * 删除type，并重定向到type首页
     * @param id
     * @param attributes
     * @return
     */
    @DeleteMapping("/types/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            typeService.delete(id);
        } catch (RuntimeException e) {
            attributes.addFlashAttribute("errMsg", "该类型下还有所属博客！！！");
            return "redirect:/admin/types";
        }
        attributes.addFlashAttribute("successMsg", "删除成功");
        return "redirect:/admin/types";
    }
}
