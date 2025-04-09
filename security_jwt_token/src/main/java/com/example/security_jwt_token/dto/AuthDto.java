package com.example.security_jwt_token.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author jiyoung
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthDto {
    private String username;
    private String password;
}
