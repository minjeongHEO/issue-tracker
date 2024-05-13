package com.issuetracker.label.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@ToString
@RequiredArgsConstructor
public class Label {
    @Id
    @Setter
    private Long id;
    private final String name;
    private final String description;
    @Column("background_color")
    private final String bgColor;
}