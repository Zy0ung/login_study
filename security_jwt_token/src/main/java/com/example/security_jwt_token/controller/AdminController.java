package com.example.security_jwt_token.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiyoung
 */
@RestController
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return "Admin Controller";
    }
}
