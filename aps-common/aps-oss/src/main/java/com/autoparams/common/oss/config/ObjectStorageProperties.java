package com.autoparams.common.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss.storage")
@Data
public class ObjectStorageProperties {
    private StorageType type;  // 新增：配置存储类型
    private S3Config s3;
    private MinioConfig minio;
    private AliyunOSSConfig aliyunOSS;
    private QiniuOSSConfig qiniuOSS;
    private LocalStorageConfig local;  // 新增：本地存储配置
}
