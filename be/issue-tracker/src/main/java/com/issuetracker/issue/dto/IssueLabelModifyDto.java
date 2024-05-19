package com.issuetracker.issue.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Getter;

@Getter
public class IssueLabelModifyDto {
    private final List<Long> labelIds;

    @JsonCreator
    public IssueLabelModifyDto(List<Long> labelIds) {
        this.labelIds = labelIds;
    }
}
