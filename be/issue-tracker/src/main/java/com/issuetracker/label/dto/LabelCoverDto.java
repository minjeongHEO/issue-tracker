package com.issuetracker.label.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@ToString
@RequiredArgsConstructor
public class LabelCoverDto {
    private final String name;
    @Column("text_color")
    private final String textColor;
    @Column("background_color")
    private final String bgColor;
}
