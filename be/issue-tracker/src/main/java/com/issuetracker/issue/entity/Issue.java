package com.issuetracker.issue.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@RequiredArgsConstructor
@Getter
@ToString
@Table
public class Issue {
    @Id
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;
    private final boolean isClosed;
    private final String memberId;
    private final Long milestoneId;
    private final Long fileId;
}
