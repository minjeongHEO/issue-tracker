package com.issuetracker.milestone.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@ToString
public class Milestone {
    @Id
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;
    private final boolean isClosed;
}
