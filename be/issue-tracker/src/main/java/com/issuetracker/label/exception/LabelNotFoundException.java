package com.issuetracker.label.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LabelNotFoundException extends CustomException {
    public LabelNotFoundException() {
        super("존재하지 않는 라벨입니다.", "NotFound.label", HttpStatus.NOT_FOUND);
    }
}