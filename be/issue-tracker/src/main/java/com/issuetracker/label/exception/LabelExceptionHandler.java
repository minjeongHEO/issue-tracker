package com.issuetracker.label.exception;

import com.issuetracker.global.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class LabelExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleNotFoundException(LabelNotFoundException e) {
        String errorMsg = "존재하지 않는 라벨 이름입니다.";
        log.error(errorMsg, e);
        ErrorResult errorResult = new ErrorResult("NotFound.label", errorMsg);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleInvalidBgColorException(InvalidBgColorException e) {
        String errorMsg = "유효하지 않은 배경색입니다.";
        log.error(errorMsg, e);
        ErrorResult errorResult = new ErrorResult("Invalid.label.bgColor", errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }
}
