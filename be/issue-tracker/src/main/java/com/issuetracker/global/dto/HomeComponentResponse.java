package com.issuetracker.global.dto;

import com.issuetracker.label.dto.LabelListDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.milestone.dto.MilestoneListDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HomeComponentResponse {
    private final List<SimpleMemberDto> assignees;
    private final LabelListDto labels;
    private final MilestoneListDto milestones;
    private final List<SimpleMemberDto> authors;
}
