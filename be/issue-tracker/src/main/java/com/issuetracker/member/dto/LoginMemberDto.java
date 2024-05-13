package com.issuetracker.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginMemberDto {
    private final String id;
    private final String nickname;
    private final String email;
}
