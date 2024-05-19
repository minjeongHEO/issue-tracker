package com.issuetracker.issue.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Getter;

@Getter
public class IssueChangeStatusDto {
    private final List<Long> issueIds;

    @JsonCreator
    public IssueChangeStatusDto(List<Long> issueIds) {
        this.issueIds = issueIds;
    }
}
