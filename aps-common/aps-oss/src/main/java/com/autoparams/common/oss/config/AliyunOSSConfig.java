package com.autoparams.common.oss.config;

import lombok.Data;

@Data
public class AliyunOSSConfig {
    private String bucketName;
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
