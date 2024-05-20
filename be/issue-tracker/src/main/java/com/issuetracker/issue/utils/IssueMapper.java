package com.issuetracker.issue.utils;

import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.issue.dto.IssueDetailDto;
import com.issuetracker.issue.dto.IssueFilterDto;
import com.issuetracker.issue.dto.IssueFilterResponse;
import com.issuetracker.issue.entity.Issue;
import com.issuetracker.label.dto.LabelCoverDto;
import com.issuetracker.label.entity.Label;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.milestone.dto.SimpleMilestoneDto;
import java.util.List;

public class IssueMapper {
    public static IssueFilterResponse toIssueFilterResponse(IssueFilterDto filterDto,
                                                            List<SimpleMemberDto> simpleMemberDtos,
                                                            List<LabelCoverDto> labelCoverDtos) {
        Long filterId = filterDto.getId();
        return IssueFilterResponse.builder()
                .id(filterId)
                .title(filterDto.getTitle())
                .authorId(filterDto.getAuthorId())
                .createDate(filterDto.getCreateDate())
                .assignees(simpleMemberDtos)
                .labels(labelCoverDtos)
                .milestoneName(filterDto.getMilestoneName())
                .build();
    }

    public static IssueDetailDto toIssueDetailDto(Issue issue, SimpleMemberDto writer, SimpleMilestoneDto milestone,
                                                  UploadedFileDto file,
                                                  List<Label> labels, List<SimpleMemberDto> assignees,
                                                  List<CommentDetailDto> comments) {
        return IssueDetailDto.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .content(issue.getContent())
                .writer(writer)
                .createDate(issue.getCreateDate())
                .isClosed(issue.isClosed())
                .milestone(milestone)
                .file(file)
                .labels(labels)
                .assignees(assignees)
                .comments(comments)
                .build();
    }
}
