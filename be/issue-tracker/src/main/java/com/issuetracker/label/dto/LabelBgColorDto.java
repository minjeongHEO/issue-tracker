package com.issuetracker.label.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class LabelBgColorDto {
    @NotBlank
    private final String bgColor;
}
