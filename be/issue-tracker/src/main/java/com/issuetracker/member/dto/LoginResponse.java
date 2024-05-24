package com.issuetracker.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {
    private final SimpleMemberDto memberProfile;
    private final TokenResponse tokenResponse;
}
