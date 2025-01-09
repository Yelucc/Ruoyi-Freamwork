package com.autoparams.common.oss.config;

import lombok.Data;

@Data
public class QiniuOSSConfig {
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String domain;
}