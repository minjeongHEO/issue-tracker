package com.issuetracker.issue.dto;

import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IssueQueryDto {
    private final Boolean isClosed;
    private final String authorId;
    private final String assigneeId;
    private final String mentionedId;
    private final Long labelId;
    private final Long milestoneId;

    public IssueQueryDto(Boolean isClosed,
                         String authorId,
                         String assigneeId,
                         String mentionedId,
                         Long labelId,
                         Long milestoneId) {
        this.isClosed = Objects.requireNonNullElse(isClosed, false);
        this.authorId = authorId;
        this.assigneeId = assigneeId;
        this.mentionedId = mentionedId;
        this.labelId = labelId;
        this.milestoneId = milestoneId;
    }
}