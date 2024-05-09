package com.issuetracker.label.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LabelDto {
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String bgColor;
}