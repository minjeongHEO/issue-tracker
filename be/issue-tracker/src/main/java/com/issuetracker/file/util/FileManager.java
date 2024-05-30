package com.issuetracker.file.util;

import java.io.IOException;
import java.util.UUID;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {
    private final Tika tika = new Tika();

    public boolean isImage(MultipartFile file) throws IOException {
        String mimeType = getMimeType(file);
        return mimeType.startsWith("image/");
    }

    public boolean isVideo(MultipartFile file) throws IOException {
        String mimeType = getMimeType(file);
        return mimeType.startsWith("video/");
    }

    public String toStoreName(String originalName) {
        String uuid = UUID.randomUUID().toString();
        String extension = extractExtension(originalName);
        return uuid + "." + extension;
    }

    private String extractExtension(String originalName) {
        int pos = originalName.lastIndexOf(".");
        return originalName.substring(pos + 1);
    }

    private String getMimeType(MultipartFile file) throws IOException {
        return tika.detect(file.getInputStream());
    }
}
