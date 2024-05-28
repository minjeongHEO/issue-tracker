package com.issuetracker.label.dto;

import com.issuetracker.label.entity.Label;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class LabelListDto {
    private final long count;
    private final List<Label> labels;
}