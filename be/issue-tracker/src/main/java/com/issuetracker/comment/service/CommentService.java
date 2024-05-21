package com.issuetracker.comment.service;

import com.issuetracker.comment.dto.CommentCreateRequest;
import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.comment.dto.CommentModifyRequest;
import com.issuetracker.comment.entity.Comment;
import com.issuetracker.comment.exception.CommentNotFoundException;
import com.issuetracker.comment.repository.CommentRepository;
import com.issuetracker.comment.util.CommentMapper;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.service.FileService;
import com.issuetracker.issue.service.IssueQueryService;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.service.MemberService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final MemberService memberService;
    private final IssueQueryService issueQueryService;
    private final CommentRepository commentRepository;
    private final FileService fileService;

    /**
     * id와 일치하는 코멘트를 찾아 세부내역을 반환한다.
     */
    @Transactional
    public CommentDetailDto getCommentDetail(Long id) {
        Comment comment = getCommentOrThrow(id);
        return addDetail(comment);
    }

    /**
     * 특정 이슈가 가지고 있는 코멘트 세부내역을 반환한다.
     */
    @Transactional
    public List<CommentDetailDto> getCommentDetails(Long issueId) {
        List<Comment> comments = commentRepository.findAllByIssueId(issueId);
        return toCommentDetails(comments);
    }

    /**
     * 새로운 코멘트를 작성한다. 작성시간은 현재 시간을 넣고, isWriter 변수는 이슈의 작성자와 동일한지 여부를 확인하여 입력한다.
     */
    @Transactional
    public CommentDetailDto createComment(CommentCreateRequest request) {
        LocalDateTime createDate = LocalDateTime.now();
        boolean isWriter = issueQueryService.hasSameWriter(request.getIssueId(), request.getWriterId());

        Comment comment = CommentMapper.toComment(request, createDate, isWriter);
        Comment saved = commentRepository.save(comment);
        log.info("새로운 코멘트가 작성되었습니다. {}", saved);

        return addDetail(saved);
    }

    /**
     * 코멘트의 본문을 수정한다. id와 일치하는 코멘트가 없다면 예외를 발생시킨다.
     */
    @Transactional
    public void modifyComment(Long id, CommentModifyRequest commentModifyRequest) {
        int affectedRow = commentRepository.updateBodyById(id, commentModifyRequest.getContent(),
                commentModifyRequest.getFileId());
        if (affectedRow == 0) {
            throw new CommentNotFoundException();
        }
    }

    /**
     * 코멘트를 삭제한다. id와 일치하는 코멘트가 없다면 예외를 발생시킨다.
     */
    @Transactional
    public void deleteComment(Long id) {
        validateCommentExists(id);
        commentRepository.deleteById(id);
    }

    private List<CommentDetailDto> toCommentDetails(List<Comment> comments) {
        List<CommentDetailDto> commentDetails = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDetailDto commentDetail = addDetail(comment);
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

    private void validateCommentExists(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }
    }

    private Comment getCommentOrThrow(Long id) {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    private CommentDetailDto addDetail(Comment comment) {
        SimpleMemberDto writer = memberService.getSimpleMemberById(comment.getMemberId());
        UploadedFileDto file = getFileByComment(comment);
        return CommentMapper.toCommentDetailDto(comment, writer, file);
    }

}
