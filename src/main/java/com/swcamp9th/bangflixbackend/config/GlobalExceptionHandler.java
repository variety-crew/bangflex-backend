package com.swcamp9th.bangflixbackend.config;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.exception.AlreadyLikedException;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import com.swcamp9th.bangflixbackend.exception.LikeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400: 잘못된 요청 예외 처리
    @ExceptionHandler({
        AlreadyLikedException.class,
        LikeNotFoundException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseMessage<>(400, e.getMessage(), null));
    }

    // 401: 지정한 리소스에 대한 권한이 없다
    @ExceptionHandler({
        InvalidUserException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleInvalidUserException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseMessage<>(401, e.getMessage(), null));
    }


}
