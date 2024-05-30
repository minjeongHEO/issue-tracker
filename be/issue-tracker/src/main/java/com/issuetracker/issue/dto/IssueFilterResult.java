package com.issuetracker.issue.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode
public class IssueFilterResult {
    private final Long id;
    private final String title;
    private final LocalDateTime createDate;
    private final Boolean isClosed;
    private final String authorId;
    private final String milestoneName;
    private final String closedCount;
    private final String openCount;
}
