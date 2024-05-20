package com.issuetracker.comment.util;

import com.issuetracker.comment.dto.CommentCreateRequest;
import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.comment.entity.Comment;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentDetailDto toCommentDetailDto(Comment comment, SimpleMemberDto writer, UploadedFileDto file) {
        return CommentDetailDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writer(writer)
                .isWriter(comment.isWriter())
                .file(file)
                .createDate(comment.getCreateDate())
                .build();
    }

    public static Comment toComment(CommentCreateRequest request, LocalDateTime createDate, boolean isWriter) {
        return new Comment(null, request.getContent(), createDate, isWriter, request.getIssueId(),
                request.getWriterId(), request.getFileId());
    }
}
