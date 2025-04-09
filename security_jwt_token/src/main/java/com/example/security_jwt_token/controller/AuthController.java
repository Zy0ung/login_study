package com.example.security_jwt_token.controller;

import com.example.security_jwt_token.dto.AuthDto;
import com.example.security_jwt_token.global.response.ResponseDto;
import com.example.security_jwt_token.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiyoung
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto<String>> joinProcess(@RequestBody AuthDto authDto) {

        joinService.joinProcess(authDto);

        return ResponseEntity.ok(new ResponseDto<>(true, "회원 가입 성공", authDto.getUsername()));
    }
}