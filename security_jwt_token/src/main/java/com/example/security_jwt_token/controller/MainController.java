package com.example.security_jwt_token.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author jiyoung
 */
@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "Main Controller ";
    }
}
