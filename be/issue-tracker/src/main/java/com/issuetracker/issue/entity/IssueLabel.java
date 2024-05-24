package com.issuetracker.issue.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@RequiredArgsConstructor
@Getter
@Table
public class IssueLabel {
    @Id
    private final Long issueId;
    private final Long labelId;
}
