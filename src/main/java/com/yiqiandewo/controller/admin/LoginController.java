package com.yiqiandewo.controller.admin;

import com.yiqiandewo.pojo.User;
import com.yiqiandewo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {
        User user = userService.check(username, password);

        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }
        session.setAttribute("message", "用户名或密码错误");
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println(session.getAttribute("user"));
        session.removeAttribute("user");
        System.out.println(session.getAttribute("user"));
        return "redirect:/admin";
    }
}
