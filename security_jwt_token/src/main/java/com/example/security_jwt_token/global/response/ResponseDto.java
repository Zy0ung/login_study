package com.example.security_jwt_token.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author jiyoung
 */
@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {
    private final boolean status; // SUCCESS 성공, FAIL 실패
    private final String msg;
    private final T data;
}
