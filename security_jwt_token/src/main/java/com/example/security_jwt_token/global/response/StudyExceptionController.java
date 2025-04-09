package com.example.security_jwt_token.global.response;

import com.example.security_jwt_token.global.response.exception.StudyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jiyoung
 */
@RestControllerAdvice
public class StudyExceptionController {

    @ExceptionHandler(value = StudyException.class)
    public ResponseEntity<?> serviceException(StudyException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(
                        new ErrorResponse(
                                e.getErrorCode().getStatus(),
                                e.getMessage(),
                                e.getErrorMap()
                        )
                );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> IllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                e.getMessage(),
                                null
                        )
                );
    }

    /**
     * 데이터 제약 조건으로 인한 Exception
     **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> BindException(BindException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                                null
                        )
                );
    }
}
