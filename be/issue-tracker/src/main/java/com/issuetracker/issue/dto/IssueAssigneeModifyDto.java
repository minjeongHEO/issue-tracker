package com.issuetracker.issue.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Getter;

@Getter
public class IssueAssigneeModifyDto {
    private final List<String> assigneeIds;

    @JsonCreator
    public IssueAssigneeModifyDto(List<String> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }
}
