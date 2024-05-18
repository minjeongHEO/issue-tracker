package com.issuetracker.issue.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IssueBodyModifyDto {
    private final String content;
    private final Long fileId;
}
