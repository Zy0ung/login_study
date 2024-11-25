package com.example.security_jwt_login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jiyoung
 */
@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "Main Controller";
    }
}