package com.issuetracker.member.exception;

import com.issuetracker.global.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MemberExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleLoginFailException(LoginFailException e) {
        String message = "아이디나 비밀번호가 일치하지 않습니다.";
        log.info(message, e);
        ErrorResult errorResult = new ErrorResult("Invalid.login", message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResult);
    }
}
