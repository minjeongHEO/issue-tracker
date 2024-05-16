package com.issuetracker.milestone.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SimpleMilestoneDto {
    private final Long id;
    private final String name;
    private final Long openIssueCount;
    private final Long closedIssueCount;
}
