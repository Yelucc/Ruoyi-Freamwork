package com.autoparams.common.oss.implement;

import com.autoparams.common.oss.ObjectStorageService;
import com.autoparams.common.oss.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.errors.MinioException;

import java.io.InputStream;

public class MinioStorage implements ObjectStorageService {
    private final MinioClient minioClient;
    private final String bucketName;

    public MinioStorage(MinioConfig minioConfig) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
        this.bucketName = minioConfig.getBucketName();
    }

    @Override
    public void upload(String fileName, InputStream inputStream) {
//        try {
//            minioClient.putObject(bucketName, fileName, String.valueOf(inputStream), inputStream.available(), null, null, null);
//        } catch (MinioException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public InputStream download(String fileName) {
//        try {
//            return minioClient.getObject(bucketName, fileName);
//        } catch (MinioException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public void delete(String fileName) {
//        try {
//            minioClient.removeObject(bucketName, fileName);
//        } catch (MinioException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String getFileUrl(String fileName) {
//        return minioClient.getObjectUrl(bucketName, fileName);
        return null;
    }

    @Override
    public boolean fileExists(String fileName) {
//        try {
//            minioClient.statObject(bucketName, fileName);
//            return true;
//        } catch (MinioException e) {
//            return false;
//        }
    }
}
