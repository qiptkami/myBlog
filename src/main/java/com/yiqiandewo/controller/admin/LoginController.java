package com.yiqiandewo.controller.admin;

import com.yiqiandewo.pojo.User;
import com.yiqiandewo.service.UserService;
import com.yiqiandewo.util.CookieUtils;
import com.yiqiandewo.util.JWTUtils;
import com.yiqiandewo.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(HttpServletRequest request) {
        Cookie token = CookieUtils.get(request, "token");
        if (token != null) {  //如果token没失效
            return "admin/index";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpServletResponse response, RedirectAttributes attributes) {
        User user = userService.check(username, MD5Utils.string2MD5(password));

        if (user == null) {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
        //验证通过
        //生成token
        String token = JWTUtils.createToken(username);
        //将token存储在cookie中
        CookieUtils.set(response, "token", token, -1);
        CookieUtils.set(response, "avatar", user.getAvatar(), -1);
        CookieUtils.set(response, "username", user.getUsername(), -1);
        CookieUtils.delete(response, "msg");
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        CookieUtils.delete(response, "avatar");
        CookieUtils.delete(response, "username");
        CookieUtils.delete(response, "token");
        return "redirect:/admin";
    }


}
