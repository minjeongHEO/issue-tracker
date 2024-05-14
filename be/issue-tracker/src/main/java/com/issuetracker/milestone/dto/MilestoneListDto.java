package com.issuetracker.milestone.dto;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MilestoneListDto {
    private final List<MilestoneDetailDto> milestoneDetailDtos;

    public List<MilestoneDetailDto> getMilestoneDetailDtos() {
        return Collections.unmodifiableList(milestoneDetailDtos);
    }
}
