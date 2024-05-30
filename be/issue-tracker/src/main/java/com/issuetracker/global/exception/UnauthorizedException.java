package com.issuetracker.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException() {
        super("사용자 인증에 실패하였습니다.", "Unauthorized.member", HttpStatus.UNAUTHORIZED);
    }
}
