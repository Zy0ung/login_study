package com.example.login.controller;

import com.example.login.dto.JoinDTO;
import com.example.login.service.JoinService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

/**
 * @author jiyoung
 */
@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
