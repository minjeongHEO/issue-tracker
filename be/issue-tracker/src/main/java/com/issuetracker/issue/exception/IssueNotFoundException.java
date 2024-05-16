package com.issuetracker.issue.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class IssueNotFoundException extends CustomException {
    public IssueNotFoundException() {
        super("존재하지 않는 이슈입니다.", "NotFound.issue", HttpStatus.NOT_FOUND);
    }
}
