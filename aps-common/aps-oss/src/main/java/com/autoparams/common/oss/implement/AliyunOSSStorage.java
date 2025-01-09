package com.autoparams.common.oss.implement;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.oss20190517.AsyncClient;
import com.aliyun.sdk.service.oss20190517.models.*;
import com.autoparams.common.oss.ObjectStorageService;
import com.autoparams.common.oss.config.AliyunOSSConfig;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public class AliyunOSSStorage implements ObjectStorageService {
    private final AsyncClient client;
    private final String bucketName;
    private final String endpoint;

    public AliyunOSSStorage(AliyunOSSConfig config) {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(config.getAccessKey())
                .accessKeySecret(config.getSecretKey())
                .build());
        this.bucketName = config.getBucketName();
        this.endpoint = config.getEndpoint();
        this.client = AsyncClient.builder()
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(endpoint)
                )
                .build();

    }

    @Override
    public void upload(String fileName, InputStream inputStream) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)

                    .build();

            PutObjectResponse response = client.putObject(request).get();
            log.info("File uploaded successfully, ETag: {}", response.toString());
        } catch (Exception e) {
            log.error("Error uploading file to Aliyun OSS", e);
        }
    }

    @Override
    public InputStream download(String fileName) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            GetObjectResponse response = client.getObject(request).get();
            return response.getBody();
        } catch (Exception e) {
            log.error("Error downloading file from Aliyun OSS", e);
            return null;
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            client.deleteObject(request).get();
            log.info("File deleted successfully: {}", fileName);
        } catch (Exception e) {
            log.error("Error deleting file from Aliyun OSS", e);
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }

    @Override
    public boolean fileExists(String fileName) {
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            client.headObject(request).get();
            return true;
        } catch (Exception e) {
            log.error("Error checking if file exists in Aliyun OSS", e);
            return false;
        }
    }

    public void close() {
        client.close();
    }
}
