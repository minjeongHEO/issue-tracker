package com.issuetracker.global.dto;

import com.issuetracker.label.dto.LabelCountDto;
import com.issuetracker.milestone.dto.MilestoneCountDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HomeComponentResponse {
    private final LabelCountDto labelCount;
    private final MilestoneCountDto milestoneCount;
}
