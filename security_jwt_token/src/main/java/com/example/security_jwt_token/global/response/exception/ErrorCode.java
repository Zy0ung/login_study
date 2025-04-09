package com.example.security_jwt_token.global.response.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author jiyoung
 */
@Getter
public enum ErrorCode {

    // 400 Bad Request

    // 401 Unauthorized,
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),

    // 403 Forbidden
    ACCOUNT_DISABLED(HttpStatus.FORBIDDEN, "Account disabled"),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found"),

    // 409 Conflict,
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists"),

    // 500 Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");

    private final HttpStatus status;
    private final String msg;

    ErrorCode(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}