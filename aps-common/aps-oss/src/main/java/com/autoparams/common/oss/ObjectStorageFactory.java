package com.autoparams.common.oss;

import com.autoparams.common.oss.config.ObjectStorageProperties;
import com.autoparams.common.oss.implement.*;

public class ObjectStorageFactory {
    public static ObjectStorageService getStorage(ObjectStorageProperties properties) {
        switch (properties.getType()) {
            case S3:
                return new S3Storage(properties.getS3());
            case MINIO:
                return new MinioStorage(properties.getMinio());
            case ALIYUNOSS:
                return new AliyunOSSStorage(properties.getAliyunOSS());
            case QINIUOSS:
                return new QiniuOSSStorage(properties.getQiniuOSS());
            case LOCAL:
                return new LocalStorage(properties.getLocal());
            default:
                throw new IllegalArgumentException("不支持的存储类型: " + properties.getType());
        }
    }
}
