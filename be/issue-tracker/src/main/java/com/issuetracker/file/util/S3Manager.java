package com.issuetracker.file.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.Date;
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

    public void uploadToS3(MultipartFile multipartFile, String storeName) throws IOException {
        ObjectMetadata metadata = getMetadata(multipartFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, getBucketKey(storeName),
                multipartFile.getInputStream(),
                metadata);
        amazonS3Client.putObject(putObjectRequest);
    }

    public String getResourceUrl(String storeName, int expirationInMinutes) {
        Date expiration = getExpiration(expirationInMinutes);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(
                storeName, expiration);
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public void deleteFile(String storeName) {
        String bucketKey = getBucketKey(storeName);
        amazonS3Client.deleteObject(bucketName, bucketKey);
    }

    private ObjectMetadata getMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        return metadata;
    }

    private String getBucketKey(String storeName) {
        return fileDir + storeName;
    }

    private Date getExpiration(long expirationInMinutes) {
        Date expiration = new Date();
        long expTimeMillis = System.currentTimeMillis() + expirationInMinutes * 60 * 1000;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String storeName, Date expiration) {
        return new GeneratePresignedUrlRequest(bucketName, getBucketKey(storeName), HttpMethod.GET)
                .withExpiration(expiration);
    }
}
