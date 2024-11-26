package com.example.security_jwt_token.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jiyoung
 */
@Controller
@ResponseBody
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return "Admin Controller";
    }
}
