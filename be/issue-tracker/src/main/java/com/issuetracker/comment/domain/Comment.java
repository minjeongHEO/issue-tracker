package com.issuetracker.comment.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
public class Comment {
    @Id
    @Setter
    private Long id;
    private final String content;
    private final LocalDateTime createDate;
    private final boolean isWriter;
    private final Long issueId;
    private final String memberId;
    private final Long fileId;
}
