package com.issuetracker.member.controller;

import com.issuetracker.member.dto.LoginResponse;
import com.issuetracker.member.dto.LoginTryDto;
import com.issuetracker.member.dto.TokenResponse;
import com.issuetracker.member.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginTryDto loginTryDto) {
        LoginResponse loginResponse = loginService.login(loginTryDto);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestHeader String authorization) {
        TokenResponse tokenResponse = loginService.refreshAccessToken(authorization);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader String authorization) {
        loginService.logout(authorization);
        return ResponseEntity.ok().build();
    }
}
