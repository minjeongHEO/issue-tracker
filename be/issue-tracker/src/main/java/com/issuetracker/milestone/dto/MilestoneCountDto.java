package com.issuetracker.milestone.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MilestoneCountDto {
    private final Long isOpened;
    private final Long isClosed;
}
