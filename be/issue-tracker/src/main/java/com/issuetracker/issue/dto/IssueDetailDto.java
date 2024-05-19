package com.issuetracker.issue.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.milestone.dto.SimpleMilestoneDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class IssueDetailDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;
    private final boolean isClosed;
    private final SimpleMemberDto writer;
    private final SimpleMilestoneDto milestone;
    private final UploadedFileDto file;
    private final List<Label> labels;
    private final List<SimpleMemberDto> assignees;
    private final List<CommentDetailDto> comments;

    @JsonGetter("isClosed")
    public boolean isClosed() {
        return isClosed;
    }
}
