package com.example.login.service;

import com.example.login.dto.JoinDTO;
import com.example.login.entity.UserEntity;
import com.example.login.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author jiyoung
 */
@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public void joinProcess(JoinDTO joinDTO){

        //이미 DB에 동일한 username을 가진 회원이 존재하는지 검사

        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());

        if (isUser) {
            return;
        }

        UserEntity user = new UserEntity();

        user.setUsername(joinDTO.getUsername());
        // 비밀번호 암호화
        user.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }
}
