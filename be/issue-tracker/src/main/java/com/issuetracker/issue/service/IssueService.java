package com.issuetracker.issue.service;

import com.issuetracker.comment.domain.Comment;
import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.comment.repository.CommentRepository;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.service.FileService;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueDetailDto;
import com.issuetracker.issue.exception.IssueNotFoundException;
import com.issuetracker.issue.repository.IssueAssigneeRepository;
import com.issuetracker.issue.repository.IssueLabelRepository;
import com.issuetracker.issue.repository.IssueRepository;
import com.issuetracker.label.domain.Label;
import com.issuetracker.label.repository.LabelRepository;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.repository.MemberRepository;
import com.issuetracker.member.service.MemberService;
import com.issuetracker.milestone.dto.SimpleMilestoneDto;
import com.issuetracker.milestone.service.MilestoneService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {
    private final IssueRepository issueRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final LabelRepository labelRepository;
    private final MilestoneService milestoneService;
    private final FileService fileService;
    private final IssueAssigneeRepository issueAssigneeRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public IssueCountDto getIssueCountDto() {
        long openedIssueCount = issueRepository.countAllByIsClosed(false);
        long closedIssueCount = issueRepository.countAllByIsClosed(true);

        return new IssueCountDto(openedIssueCount, closedIssueCount);
    }

    @Transactional
    public IssueDetailDto showIssue(Long id) {
        Issue issue = getIssue(id);
        SimpleMemberDto writer = getWriter(issue.getMemberId());
        SimpleMilestoneDto milestone = getMilestone(issue);
        UploadedFileDto file = getFileByIssue(issue);
        List<Label> labels = getIssueLabels(id);
        List<SimpleMemberDto> assignees = getIssueAssignees(id);
        List<CommentDetailDto> comments = getCommentDetails(id);

        return toIssueDetailDto(issue, writer, milestone, file, labels, assignees, comments);
    }

    private SimpleMemberDto getWriter(String memberId) {
        return memberService.getSimpleMemberDtoById(memberId);
    }

    private List<CommentDetailDto> getCommentDetails(Long id) {
        List<CommentDetailDto> commentDetails = new ArrayList<>();
        List<Comment> comments = commentRepository.findAllByIssueId(id);
        addCommentDetail(comments, commentDetails);
        return commentDetails;
    }

    private void addCommentDetail(List<Comment> comments, List<CommentDetailDto> commentDetails) {
        for (Comment comment : comments) {
            SimpleMemberDto writer = memberService.getSimpleMemberDtoById(comment.getMemberId());
            UploadedFileDto file = getFileByComment(comment);
            CommentDetailDto commentDetail = CommentDetailDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .writer(writer)
                    .isWriter(comment.isWriter())
                    .file(file)
                    .createDate(comment.getCreateDate())
                    .build();
            commentDetails.add(commentDetail);
        }
    }

    private SimpleMilestoneDto getMilestone(Issue issue) {
        Long milestoneId = issue.getMilestoneId();
        if (milestoneId == null) {
            return null;
        }
        return milestoneService.showSimpleMilestone(issue.getMilestoneId());
    }

    private UploadedFileDto getFileByIssue(Issue issue) {
        Long fileId = issue.getFileId();
        if (fileId == null) {
            return null;
        }
        return fileService.showFile(fileId);
    }

    private UploadedFileDto getFileByComment(Comment comment) {
        Long fileId = comment.getFileId();
        if (fileId == null) {
            return null;
        }
        return fileService.showFile(fileId);
    }

    private List<Label> getIssueLabels(Long id) {
        List<Long> issueLabelIds = issueLabelRepository.findAllByIssueId(id);
        return (List<Label>) labelRepository.findAllById(issueLabelIds);
    }

    private List<SimpleMemberDto> getIssueAssignees(Long id) {
        List<String> issueAssigneeIds = issueAssigneeRepository.findAllByIssueId(id);
        List<Member> members = (List<Member>) memberRepository.findAllById(issueAssigneeIds);
        return members.stream()
                .map(member -> new SimpleMemberDto(member.getId(), fileService.getImgUrlById(member.getFileId())))
                .toList();
    }

    private Issue getIssue(Long id) {
        return issueRepository.findById(id).orElseThrow(IssueNotFoundException::new);
    }

    private IssueDetailDto toIssueDetailDto(Issue issue, SimpleMemberDto writer, SimpleMilestoneDto milestone,
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
