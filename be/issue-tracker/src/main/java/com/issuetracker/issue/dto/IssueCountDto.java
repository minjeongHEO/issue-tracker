package com.issuetracker.issue.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class IssueCountDto {
    private final long openedCount;
    private final long closedCount;
}
