package com.issuetracker.file.util;

import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.entity.File;

public class FileMapper {

    public static UploadedFileDto toUploadedFileDto(File file, String url) {
        return new UploadedFileDto(file.getId(), file.getUploadName(), url);
    }
}
