package com.issuetracker.milestone.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
public class MilestoneDetailDto {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;
    private final boolean isClosed;
    private final Long openIssueCount;
    private final Long closedIssueCount;

    @JsonGetter("isClosed")
    public boolean isClosed() {
        return isClosed;
    }
}
