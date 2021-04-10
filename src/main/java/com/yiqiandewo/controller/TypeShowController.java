package com.yiqiandewo.controller;

import com.yiqiandewo.pojo.Type;
import com.yiqiandewo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String s(@PathVariable Long id,
                    @RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                    @RequestParam(name = "size", required = true, defaultValue = "6") Integer size,
                    Model model) {
        
        if (id == -1) {

        }
        return "";
    }
}
