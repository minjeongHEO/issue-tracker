package com.issuetracker.file.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Manager {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.dir}")
    private String fileDir;
    private final AmazonS3Client amazonS3Client;

    public String uploadToS3(MultipartFile multipartFile, String storeName) throws IOException {
        String bucketKey = getBucketKey(storeName);
        ObjectMetadata metadata = getMetadata(multipartFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, bucketKey, multipartFile.getInputStream(),
                metadata);
        amazonS3Client.putObject(putObjectRequest);
        return amazonS3Client.getResourceUrl(bucketName, bucketKey);
    }

    public String getResourceUrl(String storeName) {
        String bucketKey = getBucketKey(storeName);
        return amazonS3Client.getResourceUrl(bucketName, bucketKey);
    }

    private ObjectMetadata getMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        System.out.println(metadata.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        return metadata;
    }

    private String getBucketKey(String storeName) {
        return fileDir + storeName;
    }
}
