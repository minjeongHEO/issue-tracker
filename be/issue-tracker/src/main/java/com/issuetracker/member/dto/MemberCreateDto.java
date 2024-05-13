package com.issuetracker.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateDto {

    @NotBlank
    private final String id;
    @NotBlank
    private final String password;
    @NotBlank
    private final String nickname;
    @NotBlank
    private final String email;
}
