package com.issuetracker.member.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LoginFailException extends CustomException {
    public LoginFailException() {
        super("아이디나 비밀번호가 일치하지 않아 로그인에 실패하였습니다.", "Invalid.login", HttpStatus.UNAUTHORIZED);
    }
}
