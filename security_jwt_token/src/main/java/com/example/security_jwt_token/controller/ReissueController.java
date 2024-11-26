package com.example.security_jwt_token.controller;

import com.example.security_jwt_token.jwt.JWTFilter;
import com.example.security_jwt_token.jwt.JWTUtil;
import com.example.security_jwt_token.service.ReissueService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author jiyoung
 */
@Controller
@ResponseBody
//@RestController = @Controller + @ResponseBody
@RequiredArgsConstructor
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissue(request, response);
    }
}