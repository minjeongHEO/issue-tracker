package com.issuetracker.issue.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class IssueTitleModifyDto {
    @NotBlank
    private final String title;

    @JsonCreator // 단일 필드일 경우 발생하는 json 변환 오류로 생성자 직접 만들고 해당 어노테이션 붙임
    public IssueTitleModifyDto(String title) {
        this.title = title;
    }
}
