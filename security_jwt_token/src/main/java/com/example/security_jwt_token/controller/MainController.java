package com.example.security_jwt_token.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiyoung
 */
@RestController
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "Main Controller ";
    }
}
