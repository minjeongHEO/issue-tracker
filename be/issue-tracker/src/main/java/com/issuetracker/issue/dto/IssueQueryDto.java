package com.issuetracker.issue.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IssueQueryDto {
    private static final String NO_VALUES_SEPARATOR = ";";

    private final Boolean isClosed;
    private final String authorId;
    private final String assigneeId;
    private final String mentionedId;
    private final String labelName;
    private final String milestoneName;
    private final List<String> noValues;

    public IssueQueryDto(Boolean isClosed,
                         String authorId,
                         String assigneeId,
                         String mentionedId,
                         String labelName,
                         String milestoneName,
                         String noValues) {
        this.isClosed = Objects.requireNonNullElse(isClosed, false);
        this.authorId = authorId;
        this.assigneeId = assigneeId;
        this.mentionedId = mentionedId;
        this.labelName = labelName;
        this.milestoneName = milestoneName;
        this.noValues = splitNoValues(noValues);
    }

    private List<String> splitNoValues(String noValuesText) {
        if (noValuesText != null) {
            return new ArrayList<>(Arrays.asList(noValuesText.split(NO_VALUES_SEPARATOR)));
        }
        return List.of();
    }
}