package com.autoparams.common.oss.implement;

import com.autoparams.common.oss.ObjectStorageService;
import com.autoparams.common.oss.config.S3Config;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;

public class S3Storage implements ObjectStorageService {
    private final S3Client s3Client;
    private final String bucketName;

    public S3Storage(S3Config s3Config) {
        this.s3Client = S3Client.builder()
                .region(Region.of(s3Config.getRegion()))
                .credentialsProvider(() -> AwsBasicCredentials.create(s3Config.getAccessKey(), s3Config.getSecretKey()))
                .build();
        this.bucketName = s3Config.getBucketName();
    }

    public S3Storage(S3Client s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public void upload(String fileName, InputStream inputStream) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, -1));
    }

    @Override
    public InputStream download(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        return s3Client.getObject(getObjectRequest);
    }

    @Override
    public void delete(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    @Override
    public String getFileUrl(String fileName) {
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
    }

    @Override
    public boolean fileExists(String fileName) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        try {
            s3Client.headObject(headObjectRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }
}
