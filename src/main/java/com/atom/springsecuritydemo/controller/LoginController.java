package com.atom.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Atom
 */
@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(){
        System.err.println(" invoke login method");
        return "redirect:main.html";
    }


    @RequestMapping("/toMain")
    public String toMain(){
        System.err.println(" invoke toMain method");
        return "redirect:main.html";
//        return "redirect:https://www.baidu.com";
    }

    @RequestMapping("/toError")
    public String toError(){
        System.err.println(" invoke toError method");
        return "redirect:error.html";
    }
}
