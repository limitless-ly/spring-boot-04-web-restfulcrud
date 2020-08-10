package com.atguigu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){

        return "Hello World!";
    }

    // 该方法使浏览器的{"/", "/index.html"}请求，访问templates里的index.html而不再是访问public里的index.html
//    @RequestMapping({"/", "/index.html"})
//    public String index(){
//        return "index";
//    }
}
