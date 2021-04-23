package com.yiqiandewo.controller.admin;

import com.yiqiandewo.pojo.User;
import com.yiqiandewo.service.UserService;
import com.yiqiandewo.util.CookieUtils;
import com.yiqiandewo.util.JWTUtils;
import com.yiqiandewo.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
重定向代表之前的请求已结束，给客户端一个新的url，让客户端重新请求去获取资源，这个url可以是站外的，效率相对于转发要低，之前的request域已经失效，可以通过session来获得一些参数。
而转发则和它大致相反：还是同一个request请求，浏览器地址栏不发生变化，只能访问站内资源，更快
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(HttpServletRequest request, HttpServletResponse response) {
        Cookie token = CookieUtils.get(request, "token");
        if (token != null) {
            //验证token
            try {
                JWTUtils.verifyToken(token.getValue());
                CookieUtils.delete(request, response, "tokenInvalid");
                return "admin/index";
            } catch (Exception e) { //发生异常说明token失效
                e.printStackTrace();
            }
            if (CookieUtils.get(request, "tokenInvalid") == null) {
                CookieUtils.set(response, "tokenInvalid", "请先登录", -1);
            }
            CookieUtils.delete(request, response, "token");
        }
        return "admin/login";

    }

    @PostMapping("/login")
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
        User user = userService.selectOne(username);

        if (user == null) {
            attributes.addFlashAttribute("message", "用户不存在！");
            return "redirect:/admin";
        }

        if (!user.getPassword().equals(MD5Utils.string2MD5(password))) { //密码正确
            attributes.addFlashAttribute("message", "密码错误！");
            return "redirect:/admin";
        }

        //验证通过
        //生成token
        String token = JWTUtils.createToken(user);
        //将token存储在cookie中
        CookieUtils.set(response, "token", token, -1);

        CookieUtils.delete(request, response, "tokenInvalid");

        return "redirect:/admin/index";
    }

    @GetMapping("/index")
    public String mainPage() {
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.delete(request, response, "token");
        CookieUtils.delete(request, response, "tokenInvalid");
        return "redirect:/admin";
    }

}
