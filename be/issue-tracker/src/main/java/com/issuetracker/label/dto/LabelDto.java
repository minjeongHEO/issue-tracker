package com.issuetracker.label.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class LabelDto {
    @NotBlank
    private final String name;
    private final String description;
    @NotBlank
    private final String textColor;
    @NotBlank
    private final String bgColor;
}