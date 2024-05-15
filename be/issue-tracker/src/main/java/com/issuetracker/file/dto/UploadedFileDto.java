package com.issuetracker.file.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UploadedFileDto {
    private final Long id;
    private final String uploadName;
    private final String storeName;
    private final String url;
}
