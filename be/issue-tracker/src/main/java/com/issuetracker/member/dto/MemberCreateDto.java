package com.issuetracker.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDto {

    @NotBlank
    private String id;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
}
