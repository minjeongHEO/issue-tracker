package com.issuetracker.member.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CustomException {
    public MemberNotFoundException() {
        super("해당 멤버는 존재하지 않습니다.", "NotFound.member", HttpStatus.NOT_FOUND);
    }
}
