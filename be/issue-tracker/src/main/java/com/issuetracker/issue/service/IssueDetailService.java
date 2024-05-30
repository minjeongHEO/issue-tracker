package com.issuetracker.issue.service;

import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.comment.service.CommentService;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.service.FileService;
import com.issuetracker.issue.dto.IssueDetailDto;
import com.issuetracker.issue.entity.Issue;
import com.issuetracker.issue.util.IssueMapper;
import com.issuetracker.label.entity.Label;
import com.issuetracker.label.service.LabelService;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.service.MemberService;
import com.issuetracker.milestone.dto.SimpleMilestoneDto;
import com.issuetracker.milestone.service.MilestoneService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueDetailService {
    private final IssueQueryService issueQueryService;
    private final LabelService labelService;
    private final MilestoneService milestoneService;
    private final FileService fileService;
    private final CommentService commentService;
    private final MemberService memberService;

    /**
     * 사용자가 요청한 id와 일치하는 이슈 상세 정보를 반환한다. 일치하는 id가 없으면 예외를 발생시킨다.
     */
    @Transactional
    public IssueDetailDto showIssue(Long id) {
        Issue issue = getIssue(id);
        SimpleMemberDto writer = getWriter(issue.getMemberId());
        SimpleMilestoneDto milestone = getMilestone(issue);
        UploadedFileDto file = getFileByIssue(issue);
        List<Label> labels = getIssueLabels(id);
        List<SimpleMemberDto> assignees = getIssueAssignees(id);
        List<CommentDetailDto> comments = commentService.getCommentDetails(id);

        return IssueMapper.toIssueDetailDto(issue, writer, milestone, file, labels, assignees, comments);
    }

    private SimpleMemberDto getWriter(String memberId) {
        return memberService.getSimpleMemberById(memberId);
    }

    private SimpleMilestoneDto getMilestone(Issue issue) {
        Long milestoneId = issue.getMilestoneId();
        if (milestoneId == null) {
            return null;
        }
        return milestoneService.showMilestoneCover(issue.getMilestoneId());
    }

    private UploadedFileDto getFileByIssue(Issue issue) {
        Long fileId = issue.getFileId();
        if (fileId == null) {
            return null;
        }
        return fileService.showFile(fileId);
    }

    private List<Label> getIssueLabels(Long id) {
        List<Long> issueLabelIds = issueQueryService.findLabelIdsByIssueId(id);
        return labelService.findLabelsByIds(issueLabelIds);
    }

    private List<SimpleMemberDto> getIssueAssignees(Long id) {
        List<String> issueAssigneeIds = issueQueryService.findAssigneeIdsByIssueId(id);
        return issueAssigneeIds.stream()
                .map(memberService::getSimpleMemberById)
                .collect(Collectors.toList());
    }

    private Issue getIssue(Long id) {
        return issueQueryService.getIssueOrThrow(id);
    }
}
