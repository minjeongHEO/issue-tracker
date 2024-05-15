package com.issuetracker.file.service;

import com.issuetracker.file.Repository.FileRepository;
import com.issuetracker.file.domain.File;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.exception.NotAllowedFileFormatException;
import com.issuetracker.file.util.FileManager;
import com.issuetracker.file.util.S3Manager;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final S3Manager s3Manager;
    private final FileManager fileManager;
    private final FileRepository fileRepository;

    @Transactional
    public UploadedFileDto uploadFile(MultipartFile multipartFile) throws IOException {
        validateFileFormat(multipartFile);

        String uploadName = multipartFile.getOriginalFilename();
        String storeName = fileManager.toStoreName(uploadName);

        String url = s3Manager.uploadToS3(multipartFile, storeName);

        File file = fileRepository.save(new File(uploadName, storeName));
        UploadedFileDto uploadedFileDto = toUploadedFileDto(file, url);

        log.info("새로운 파일이 저장되었습니다. {}", uploadedFileDto);
        return uploadedFileDto;
    }

    private void validateFileFormat(MultipartFile multipartFile) throws IOException {
        if (!fileManager.isImage(multipartFile) && !fileManager.isVideo(multipartFile)) {
            throw new NotAllowedFileFormatException();
        }
    }

    private UploadedFileDto toUploadedFileDto(File file, String url) {
        return new UploadedFileDto(file.getId(), file.getUploadName(),
                file.getStoreName(), url);
    }
}
