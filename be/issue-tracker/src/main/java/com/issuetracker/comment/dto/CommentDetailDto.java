package com.issuetracker.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class CommentDetailDto {
    private final Long id;
    private final String content;
    private final LocalDateTime createDate;
    @JsonProperty("isWriter")
    private final boolean isWriter;
    private final SimpleMemberDto writer;
    private final UploadedFileDto file;
}
