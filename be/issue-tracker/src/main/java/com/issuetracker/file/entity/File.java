package com.issuetracker.file.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@ToString
public class File {
    @Id
    private final Long id;
    private final String uploadName;
    private final String storeName;
}
