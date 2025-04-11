package com.example.security_jwt_token.service;

import com.example.security_jwt_token.dto.AuthDto;
import com.example.security_jwt_token.entity.UserEntity;
import com.example.security_jwt_token.global.response.exception.ErrorCode;
import com.example.security_jwt_token.global.response.exception.StudyException;
import com.example.security_jwt_token.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jiyoung
 */
@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void joinProcess(AuthDto authDto) {

        String username = authDto.getUsername();
        String password = authDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            System.out.println("join Failed");
            throw new StudyException(ErrorCode.USER_ALREADY_EXISTS);
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);
    }
}