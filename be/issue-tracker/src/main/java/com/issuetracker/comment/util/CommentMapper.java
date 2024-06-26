package com.issuetracker.comment.util;

import com.issuetracker.comment.dto.CommentCreateRequest;
import com.issuetracker.comment.dto.CommentDetailDto;
import com.issuetracker.comment.entity.Comment;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

    public static Comment toComment(CommentCreateRequest request, boolean isWriter) {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime seoulZonedDateTime = ZonedDateTime.now(seoulZoneId);
        LocalDateTime createDate = seoulZonedDateTime.toLocalDateTime();
        
        return new Comment(null, request.getContent(), createDate, isWriter, request.getIssueId(),
                request.getWriterId(), request.getFileId());
    }
}
