package com.issuetracker.file.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CustomException {
    public FileNotFoundException() {
        super("존재하지 않는 파일 id 입니다.", "NotFound.file", HttpStatus.NOT_FOUND);
    }
}
