package com.issuetracker.milestone.exception;

import com.issuetracker.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MilestoneNotFoundException extends CustomException {
    public MilestoneNotFoundException() {
        super("존재하지 않는 마일스톤입니다.", "NotFound.milestone", HttpStatus.NOT_FOUND);
    }
}
