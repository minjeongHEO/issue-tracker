package com.issuetracker.file.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NotAllowedFileFormatException extends CustomException {
    public NotAllowedFileFormatException() {
        super("허용되지 않는 파일 형식입니다.", "Invalid.file.format", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
