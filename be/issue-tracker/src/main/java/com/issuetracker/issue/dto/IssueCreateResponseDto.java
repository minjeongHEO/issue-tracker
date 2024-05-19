package com.issuetracker.issue.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class IssueCreateResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String authorId;
    private final LocalDateTime createDate;
    private final boolean isClosed;
    private final Long milestoneId;
    private final Long fileId;
    private final List<Long> labelIds;
    private final List<String> assigneeIds;

    @JsonGetter("isClosed")
    public boolean isClosed() {
        return isClosed;
    }
}
