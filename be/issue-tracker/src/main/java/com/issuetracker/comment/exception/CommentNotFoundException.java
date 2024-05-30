package com.issuetracker.comment.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CustomException {
    public CommentNotFoundException() {
        super("존재하지 않는 코멘트 id 입니다.", "NotFound.comment", HttpStatus.NOT_FOUND);
    }
}
