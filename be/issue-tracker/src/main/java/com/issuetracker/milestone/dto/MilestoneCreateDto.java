package com.issuetracker.milestone.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MilestoneCreateDto {
    @NotBlank
    private final String name;
    private final String description;
    private final LocalDateTime dueDate;
}
