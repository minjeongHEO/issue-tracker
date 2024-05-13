package com.issuetracker.file.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
public class File {
    @Id
    @Setter
    private Long id;
    private final String uploadName;
    private final String storeName;
}
