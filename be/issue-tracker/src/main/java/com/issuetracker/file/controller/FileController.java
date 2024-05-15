package com.issuetracker.file.controller;

import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.service.FileService;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<UploadedFileDto> uploadFile(@RequestParam MultipartFile file) throws IOException {
        UploadedFileDto uploadedFileDto = fileService.uploadFile(file);
        URI location = URI.create(String.format("/api/files/%s", uploadedFileDto.getId()));
        return ResponseEntity.created(location).body(uploadedFileDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UploadedFileDto> showFile(@PathVariable Long id) {
        UploadedFileDto uploadedFileDto = fileService.showFile(id);
        return ResponseEntity.ok(uploadedFileDto);
    }
}
