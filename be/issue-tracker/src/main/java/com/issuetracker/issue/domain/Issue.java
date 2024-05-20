package com.issuetracker.issue.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@ToString
public class Issue {
    @Id
    @Setter
    private Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;
    private final boolean isClosed;
    private final String memberId;
    private final Long milestoneId;
    private final Long fileId;
}
