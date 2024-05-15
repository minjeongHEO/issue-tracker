package com.issuetracker.file.util;

import java.util.UUID;

public class FileNameManager {

    public static String toStoreName(String originalName) {
        String uuid = UUID.randomUUID().toString();
        String extension = extractExtension(originalName);
        return uuid + "." + extension;
    }

    private static String extractExtension(String originalName) {
        int pos = originalName.lastIndexOf(".");
        return originalName.substring(pos + 1);
    }
}
