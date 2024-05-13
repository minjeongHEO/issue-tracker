package com.issuetracker.milestone.exception;

import com.issuetracker.global.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MilestoneExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleNotFoundException(MilestoneNotFoundException e) {
        String errorMsg = "존재하지 않는 마일스톤입니다.";
        log.error(errorMsg, e);
        ErrorResult errorResult = new ErrorResult("NotFound.milestone", errorMsg);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }
}
