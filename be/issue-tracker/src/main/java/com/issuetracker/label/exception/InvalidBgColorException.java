package com.issuetracker.label.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidBgColorException extends CustomException {
    public InvalidBgColorException() {
        super("유효하지 않은 배경색입니다.", "Invalid.label.bgColor", HttpStatus.BAD_REQUEST);
    }
}