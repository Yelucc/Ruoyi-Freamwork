package com.autoparams.common.oss.config;

import lombok.Data;

@Data
public class S3Config {
    private String bucketName;
    private String region;
    private String accessKey;
    private String secretKey;
}