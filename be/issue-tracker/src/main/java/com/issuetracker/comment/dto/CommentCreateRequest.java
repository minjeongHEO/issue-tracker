package com.issuetracker.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentCreateRequest {
    @NotBlank
    private final String content;
    @NotNull
    private final Long issueId;
    @NotNull
    private final String writerId; // 현재 로그인 유지 기능이 없으므로 해당 데이터 주입받아야함.
    private final Long fileId;
}
