package com.issuetracker.label.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@RequiredArgsConstructor
@Table("label")
public class Label {
    @Id
    private final String name;
    private final String description;
    @Column("background_color")
    private final String bgColor;
}