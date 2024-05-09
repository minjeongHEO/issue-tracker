package com.issuetracker.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleValidationExceptions(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult();

        errorResult.add("fieldErrors", e.getBindingResult().getFieldErrors());
        log.error("바인딩 에러가 발생하였습니다.", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleDuplicateKeyExceptions(DuplicateKeyException e) {
        log.error("중복키 에러가 발생하였습니다.", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleNotFoundExceptions(IncorrectUpdateSemanticsDataAccessException e) {
        log.error("존재하지 않는 리소스입니다.", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
