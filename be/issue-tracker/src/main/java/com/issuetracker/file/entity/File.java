package com.issuetracker.file.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@RequiredArgsConstructor
@Getter
@ToString
@Table
public class File {
    @Id
    private final Long id;
    private final String uploadName;
    private final String storeName;
}
