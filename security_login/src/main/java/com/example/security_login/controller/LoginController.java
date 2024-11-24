package com.example.security_login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiyoung
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }
}
