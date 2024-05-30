package com.issuetracker.milestone.util;

import com.issuetracker.milestone.dto.MilestoneCreateDto;
import com.issuetracker.milestone.dto.MilestoneDetailDto;
import com.issuetracker.milestone.entity.Milestone;

public class MilestoneMapper {
    public static MilestoneDetailDto toMilestoneDetailDto(Milestone milestone, Long openIssueCount,
                                                          Long closedIssueCount) {
        return MilestoneDetailDto.builder()
                .id(milestone.getId())
                .name(milestone.getName())
                .description(milestone.getDescription())
                .dueDate(milestone.getDueDate())
                .openIssueCount(openIssueCount)
                .closedIssueCount(closedIssueCount)
                .isClosed(milestone.isClosed())
                .build();
    }

    public static Milestone toMilestone(MilestoneCreateDto milestoneCreateDto, Long id, boolean isClosed) {
        return new Milestone(id, milestoneCreateDto.getName(), milestoneCreateDto.getDescription(),
                milestoneCreateDto.getDueDate(), isClosed);
    }
}
