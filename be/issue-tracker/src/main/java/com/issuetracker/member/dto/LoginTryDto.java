package com.issuetracker.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginTryDto {
    @NotBlank
    private final String id;
    @NotBlank
    private final String password;
}
