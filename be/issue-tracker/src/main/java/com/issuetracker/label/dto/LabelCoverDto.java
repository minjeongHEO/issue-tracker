package com.issuetracker.label.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@ToString
@RequiredArgsConstructor
public class LabelCoverDto {
    @NotBlank
    private final String name;
    @NotBlank
    @Column("background_color")
    private final String bgColor;
}
