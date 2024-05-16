package com.issuetracker.issue.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@Builder
@EqualsAndHashCode
public class IssueFilterDto {
    private final Long id;
    private final String title;
    private final String authorId;
    private final LocalDateTime createDate;
    private final String milestoneName;
}
