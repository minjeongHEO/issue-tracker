package com.issuetracker.file.service;

import com.issuetracker.file.Repository.FileRepository;
import com.issuetracker.file.domain.File;
import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.exception.FileNotFoundException;
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
    public static final int EXPIRATION_IN_MINUTES = 60;
    private final S3Manager s3Manager;
    private final FileManager fileManager;
    private final FileRepository fileRepository;

    @Transactional
    public UploadedFileDto uploadFile(MultipartFile multipartFile) throws IOException {
        validateFileFormat(multipartFile);

        String uploadName = multipartFile.getOriginalFilename();
        String storeName = fileManager.toStoreName(uploadName);

        File file = fileRepository.save(new File(uploadName, storeName));
        s3Manager.uploadToS3(multipartFile, storeName);
        log.info("새로운 파일이 저장되었습니다. {}", file);

        return toUploadedFileDto(file, s3Manager.getResourceUrl(file.getStoreName(), EXPIRATION_IN_MINUTES));
    }

    @Transactional(readOnly = true)
    public UploadedFileDto showFile(Long id) {
        File file = getFileOrThrow(id);
        String url = s3Manager.getResourceUrl(file.getStoreName(), EXPIRATION_IN_MINUTES);
        return toUploadedFileDto(file, url);
    }

    @Transactional
    public void deleteFile(Long id) {
        File file = getFileOrThrow(id);
        fileRepository.deleteById(id);
        s3Manager.deleteFile(file.getStoreName());
        log.info("파일이 삭제되었습니다. {}", file);
    }

    @Transactional(readOnly = true)
    public String getImgUrlById(Long id) {
        File file = getFileOrThrow(id);
        return s3Manager.getResourceUrl(file.getStoreName(), EXPIRATION_IN_MINUTES);
    }

    private File getFileOrThrow(Long id) {
        return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    private void validateFileFormat(MultipartFile multipartFile) throws IOException {
        if (!fileManager.isImage(multipartFile) && !fileManager.isVideo(multipartFile)) {
            throw new NotAllowedFileFormatException();
        }
    }

    private UploadedFileDto toUploadedFileDto(File file, String url) {
        return new UploadedFileDto(file.getId(), file.getUploadName(), url);
    }
}
