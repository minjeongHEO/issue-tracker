package com.issuetracker.milestone.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
public class Milestone {
    @Setter
    @Id
    private Long id;
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;
    private final boolean isClosed;
}
