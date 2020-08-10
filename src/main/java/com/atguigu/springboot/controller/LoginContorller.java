package com.atguigu.springboot.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import sun.awt.SunHints;
import sun.misc.Request;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginContorller {

    //    @PutMapping
    //@RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map,
                        HttpSession session) {
        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            // 登录成功
//            return "dashboard";// templates/dashboard.html，前面的templates和后面的html不用写

            // 登录成功后，记录已登录用户信息
            session.setAttribute("loginUser", username);
            // 登陆成功后，进入菜单页面，在菜单页面F5刷新，会重提交login页面的表单数据，为了防止表单重复提交，可以重定向主页
            return "redirect:/main.html";// 即登录成功后，进入main.html页面。但是main已经通过试图映射器映射到了dashboard页面
            // 若不加登录拦截器，则可以通过http://localhost:8080/crud/main.html直接进入菜单页面
        } else {
            // 登录失败
            map.put("msg", "用户名密码错误！");
            return "login";
        }

    }

}
