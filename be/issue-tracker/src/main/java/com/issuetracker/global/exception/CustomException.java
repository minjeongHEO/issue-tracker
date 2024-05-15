package com.issuetracker.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomException extends RuntimeException {
    private final String errorCode;
    private final HttpStatusCode httpStatusCode;

    public CustomException(String message, String errorCode, HttpStatus httpStatusCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }
}
