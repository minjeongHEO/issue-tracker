package com.issuetracker.global.exception;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("바인딩 에러가 발생하였습니다.", e);
        FieldError fieldError = e.getBindingResult().getFieldError();

        String code = String.format("%s.%s", fieldError.getCode(), fieldError.getField());
        ErrorResult errorResult = new ErrorResult(code, fieldError.getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleDuplicateKeyExceptions(DuplicateKeyException e) {
        log.error("중복키 에러가 발생하였습니다.", e);
        ErrorResult errorResult = new ErrorResult("Duplicate.id", "중복된 아이디입니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleNotFoundExceptions(IncorrectUpdateSemanticsDataAccessException e) {
        log.error("존재하지 않는 리소스입니다.", e);
        ErrorResult errorResult = new ErrorResult("NotFound.id", "존재하지 않는 리소스입니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleIOException(IOException ex) {
        String errorMsg = "파일 업로드 중 오류가 발생했습니다.";
        log.error(errorMsg, ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResult("UploadFail.file", errorMsg));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleCustomExceptions(CustomException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResult(e.getErrorCode(), e.getMessage()));
    }
}





