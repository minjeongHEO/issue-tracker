package com.issuetracker.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentModifyRequest {
    @NotBlank
    private final String content;
    private final Long fileId;
}
