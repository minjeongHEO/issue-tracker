package com.issuetracker.milestone.dto;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MilestoneListDto {
    private final List<MilestoneDetailDto> milestoneDetailDtos;
    @Getter
    private final Long count;

    public List<MilestoneDetailDto> getMilestoneDetailDtos() {
        return Collections.unmodifiableList(milestoneDetailDtos);
    }
}
