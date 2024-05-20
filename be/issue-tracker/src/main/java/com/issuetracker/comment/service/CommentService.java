package com.issuetracker.comment.service;

import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.comment.entity.Comment;
import com.issuetracker.comment.repository.CommentRepository;
import com.issuetracker.comment.util.CommentMapper;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.service.FileService;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.service.MemberService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final MemberService memberService;
    private final CommentRepository commentRepository;
    private final FileService fileService;

    public List<CommentDetailDto> getCommentDetails(Long id) {
        List<Comment> comments = commentRepository.findAllByIssueId(id);
        return toCommentDetails(comments);
    }

    private List<CommentDetailDto> toCommentDetails(List<Comment> comments) {
        List<CommentDetailDto> commentDetails = new ArrayList<>();
        for (Comment comment : comments) {
            SimpleMemberDto writer = memberService.getSimpleMemberById(comment.getMemberId());
            UploadedFileDto file = getFileByComment(comment);
            CommentDetailDto commentDetail = CommentMapper.toCommentDetailDto(comment, writer, file);
            commentDetails.add(commentDetail);
        }
        return commentDetails;
    }

    private UploadedFileDto getFileByComment(Comment comment) {
        Long fileId = comment.getFileId();
        if (fileId == null) {
            return null;
        }
        return fileService.showFile(fileId);
    }
}
