package com.issuetracker.milestone.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MilestoneListDto {
    private final Long count;
    private final List<MilestoneDetailDto> milestoneDetailDtos;
}
