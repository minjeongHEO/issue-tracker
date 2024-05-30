package com.issuetracker.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SimpleMemberDto {
    private final String id;
    private final String imgUrl;
}
