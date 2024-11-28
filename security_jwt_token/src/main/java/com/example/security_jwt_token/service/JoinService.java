package com.example.security_jwt_token.service;

import com.example.security_jwt_token.dto.JoinDTO;
import com.example.security_jwt_token.entity.UserEntity;
import com.example.security_jwt_token.repository.UserRepository;

import org.apache.catalina.connector.Response;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import static jakarta.servlet.http.HttpServletResponse.SC_CONFLICT;

/**
 * @author jiyoung
 */
@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HttpServletResponse httpServletResponse;

    @Transactional
    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            System.out.println("join Failed");
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");
        userRepository.save(data);
    }
}