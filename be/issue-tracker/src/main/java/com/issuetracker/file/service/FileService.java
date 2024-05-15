package com.issuetracker.file.service;

import com.issuetracker.file.Repository.FileRepository;
import com.issuetracker.file.domain.File;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.util.FileNameManager;
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
    private final FileRepository fileRepository;

    @Transactional
    public UploadedFileDto uploadFile(MultipartFile multipartFile) throws IOException {
        String uploadName = multipartFile.getOriginalFilename();
        String storeName = FileNameManager.toStoreName(uploadName);

        String url = s3Manager.uploadToS3(multipartFile, storeName);

        File file = fileRepository.save(new File(uploadName, storeName));
        UploadedFileDto uploadedFileDto = toUploadedFileDto(file, url);

        log.info("새로운 파일이 저장되었습니다. {}", uploadedFileDto);
        return uploadedFileDto;
    }

    private UploadedFileDto toUploadedFileDto(File file, String url) {
        return new UploadedFileDto(file.getId(), file.getUploadName(),
                file.getStoreName(), url);
    }
}
