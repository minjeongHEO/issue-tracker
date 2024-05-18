package com.issuetracker.issue.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class IssueMilestoneModifyDto {
    private final Long milestoneId;

    @JsonCreator
    public IssueMilestoneModifyDto(Long milestoneId) {
        this.milestoneId = milestoneId;
    }
}
